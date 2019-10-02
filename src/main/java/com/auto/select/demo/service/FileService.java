package com.auto.select.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/19 22:25
 */
@Service
public class FileService {
    private final String filePath ="G:/AutoSelectFiles/";
    private final String[] fileType = {".txt",".fjs"};
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Transactional(rollbackFor = Exception.class)
    public boolean upload(MultipartFile file, HttpSession session){
        if(file.isEmpty()){
            throw new RuntimeException("上传文件失败：选择的文件为空！");
        }

        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);

        String suffixName = fileName.substring(fileName.indexOf("."));
        logger.info("文件的后缀为：" + suffixName);

        boolean isNotType = true;

        for(String s: fileType){
            if(s.equals(suffixName)){
                isNotType = false;
            }
        }

        if(isNotType){
            throw new RuntimeException("上传文件失败：文件后缀必须为:" + fileType[0] + fileType[1]);
        }

        File uploadFile = new File(filePath + fileName);

        if(!uploadFile.getParentFile().exists()){
            uploadFile.getParentFile().mkdir();
        }

        try{
            file.transferTo(uploadFile);
            session.setAttribute("fileName", fileName);
            return true;
        }  catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw  new RuntimeException("上传文件失败：未知错误！");
    }
}
