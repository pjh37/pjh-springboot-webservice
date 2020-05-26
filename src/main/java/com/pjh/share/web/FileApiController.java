package com.pjh.share.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequiredArgsConstructor
@RestController
public class FileApiController {
    @GetMapping("/file/{fileName}")
    public void getImage(HttpServletResponse response, @PathVariable String fileName)throws Exception{
        int len;
        int bufferSize=1024;
        String fullPath="C:"+File.separator+"uploads"+File.separator+fileName+".jpg";
        BufferedOutputStream out=null;
        InputStream in=null;
        try{
            File file=new File(fullPath);
            if(file.exists()){
                in=new FileInputStream(file);
                out=new BufferedOutputStream(response.getOutputStream());
                byte[] buf=new byte[bufferSize];
                while((len=in.read(buf))>0){
                    out.write(buf,0,len);
                }
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            if(out!=null)out.flush();
            if(out!=null)out.close();
            if(in!=null)in.close();
        }
    }
}
