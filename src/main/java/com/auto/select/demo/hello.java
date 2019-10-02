package com.auto.select.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/7 15:03
 */
@RestController
public class hello {
    @RequestMapping("/hello")
    public String hello(){
        return "hello spring-boot";
    }
}
