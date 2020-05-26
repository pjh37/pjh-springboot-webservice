package com.pjh.share.web;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.service.VideoService;
import com.pjh.share.web.dto.VideoUploadRequestDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.result.Output;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    @GetMapping("/watch")
    public String video(Model model,@CurrentUser Account account){
        if(account!=null){
            model.addAttribute("name",account.getName());
        }
        return "watch";
    }

    @PostMapping("/api/video")
    @ResponseBody
    public Long videoUpload(VideoUploadRequestDto requestDto)throws Exception{
        return videoService.save(requestDto);
    }




    @GetMapping("/watch/{video_name}")
    public void videoStream(@PathVariable("video_name") String videoName, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String filename=videoName;
        String fullPath="C:"+File.separator+"uploads"+File.separator+filename+".mp4";
        System.out.println("filename : "+filename);
        System.out.println("fullpath : "+fullPath);
        File file=new File(fullPath);
        if(!file.exists())throw new FileNotFoundException();

        RandomAccessFile randomFile=new RandomAccessFile(file,"r");

        long rangeStart=0;//요청시작
        long rangeEnd=0;//요청 끝
        boolean isPart=false;//부분 요청일 경우 true

        try{
            //동영상 파일 크기
            long fileSize=randomFile.length();
            String range=request.getHeader("range");
            System.out.println("range : "+range);
            //range의 형식  "bytes={start}-{end}" =>  단, 브라우저마다 다르다
            if(range!=null){
                if(range.endsWith("-")){
                    range=range+(fileSize-1);
                }
                int idx=range.trim().indexOf("-");
                rangeStart=Long.parseLong(range.substring(6,idx));
                rangeEnd=Long.parseLong(range.substring(idx+1));
                if(rangeStart>0){
                    isPart=true;
                }
            }else{
                rangeStart=0;
                rangeEnd=fileSize-1;//0부터 시작하므로 -1을 해준다
            }
            //전송 파일 크기
            long partSize=rangeEnd-rangeStart+1;

            //전송 시작
            response.reset();
            response.setStatus(isPart?206:200);
            response.setContentType("video/mp4");

            response.setHeader("Content-Range","bytes "+rangeStart+"-"+rangeEnd+"/"+fileSize);
            response.setHeader("Accept-Ranges","bytes");
            response.setHeader("Content-Length",""+partSize);

            OutputStream out=response.getOutputStream();
            randomFile.seek(rangeStart);

            int bufferSize=8*1024;
            byte[] buf=new byte[bufferSize];
            do{
                int block=partSize> bufferSize ? bufferSize:(int)partSize;
                int len=randomFile.read(buf,0,block);
                out.write(buf,0,len);
                partSize-=block;
            }while (partSize>0);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            randomFile.close();
        }
    }
}
