package com.auto.select.demo.Contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/15 22:24
 */
@Controller
public class HtmlController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }



    @RequestMapping("/autoselect")
    public String autoselect() {
        return "autoselect";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
