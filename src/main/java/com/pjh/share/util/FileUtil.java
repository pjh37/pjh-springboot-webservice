package com.pjh.share.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUtil {

    public static void upload(MultipartFile file,String fileName)throws Exception{
        Integer idx=file.getOriginalFilename().indexOf(".");
        String mimeType=file.getOriginalFilename().substring(idx);
        String dirPath="C:"+File.separator+"uploads";
        File dir=new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File dest=new File(dirPath+File.separator+fileName+mimeType);
        file.transferTo(dest);
    }
}
