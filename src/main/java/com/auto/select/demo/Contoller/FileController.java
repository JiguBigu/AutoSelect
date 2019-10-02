package com.auto.select.demo.Contoller;

import com.auto.select.demo.service.FileService;
import com.auto.select.demo.test.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/19 22:21
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;
    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file, HttpSession session){
        logger.info("session获取的user：" + session.getAttribute("user"));
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", fileService.upload(file ,session));
        modelMap.put("fileName", file.getOriginalFilename());
        return modelMap;
    }

    @RequestMapping(value = "/upload2")
    public String upload2(@RequestParam("file") MultipartFile file, Model model, HttpSession session){
        session.setAttribute("fileName", file.getOriginalFilename());
        model.addAttribute("fileName", file.getOriginalFilename());
        session.setAttribute("success", fileService.upload(file ,session));
        return  new HtmlController().autoselect();
    }
}
