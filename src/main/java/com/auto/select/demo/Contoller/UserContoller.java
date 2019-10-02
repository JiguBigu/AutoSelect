package com.auto.select.demo.Contoller;

import com.auto.select.demo.entity.User;
import com.auto.select.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/14 18:55
 */
@RestController
@RequestMapping("/user")
public class UserContoller {
    @Autowired
    UserService userService;
    HtmlController htmlController = new HtmlController();
    private final Logger logger = LoggerFactory.getLogger(UserContoller.class);

    @RequestMapping("/getUserByName")
    public Map<String, Object> getUserByName(String username){
        Map<String, Object> modelMap = new HashMap<>();
        User user = userService.getUserByUsername(username);
        modelMap.put("user", user);
        return modelMap;
    }

    @RequestMapping("/addUser")
    public Map<String, Object> addUser(@RequestBody User user){
        Map<String, Object> modelMap = new HashMap<>();
        user.setCreateTime(new Date());
        user.setLastEditTime(new Date());
        user.setPermission(1);
        modelMap.put("success", userService.addUser(user));
        return modelMap;
    }

    @RequestMapping("modifyUser")
    public Map<String, Object> modifyUser(@RequestBody User user){
        Map<String, Object> modelMap = new HashMap<>();
        user.setLastEditTime(new Date());
        modelMap.put("success", userService.modifyUser(user));
        return modelMap;
    }

    @RequestMapping("/deleteUserByUsername")
    public Map<String, Object> deleteUserByUsername(String username){
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", userService.deleteUser(username));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public Map<String, Object> register(String username, String password){
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", userService.register(username, password));
        return modelMap;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CrossOrigin
    public Map<String, Object> login(String username, String password, HttpSession session){
        Map<String, Object> modelMap = new HashMap<>();
        boolean success = userService.login(username, password, session);
        modelMap.put("success", success);
        htmlController.autoselect();
        return modelMap;
    }



}
