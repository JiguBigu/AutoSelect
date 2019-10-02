package com.auto.select.demo.service.impl;

import com.auto.select.demo.Contoller.UserContoller;
import com.auto.select.demo.dao.UserDao;
import com.auto.select.demo.entity.User;
import com.auto.select.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/7 18:13
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User getUserByUsername(String name) {
        return userDao.queryUserByUsername(name);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addUser(User user) {
        if(user.getUsername() != null && !"".equals(user.getUsername())){
            user.setCreateTime(new Date());
            user.setLastEditTime(new Date());
            System.out.println(user);
            try{
                int effectNum = userDao.insertUser(user);
                if(effectNum == 1){
                    return  true;
                }else{
                    throw new RuntimeException("插入用户失败");
                }
            }catch (Exception e){
                throw new RuntimeException("插入用户失败：" + e.getMessage());
            }
        } else{
            throw new RuntimeException("插入用户的用户名不能为空");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyUser(User user) {
        if(user.getUsername() != null && !"".equals(user.getUsername())){
            user.setLastEditTime(new Date());
            try{
                int effectNum = userDao.updateUser(user);
                if(effectNum > 0){
                    return true;
                }else {
                    throw new RuntimeException("修改用户失败");
                }
            }catch (Exception e) {
                throw  new RuntimeException("修改用户失败：" + e.getMessage());
            }
        }else {
            throw new RuntimeException("修改用户时，用户名不能为空");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(String username) {
        try{
           int effectNum = userDao.deleteUser(username);
           if(effectNum > 0){
               return true;
           }else {
               throw new RuntimeException("删除用户失败");
           }
        }catch (Exception e){
            throw  new RuntimeException("删除用户失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(String username, String password) {
        boolean success;
        User user = getUserByUsername(username);
        //判断数据库中是否有已存在用户
        if(user != null){
            throw new RuntimeException("已存在该用户,无法重复注册!");
        }else {
            User registerUser = new User();
            registerUser.setUsername(username);
            registerUser.setPassword(password);
            registerUser.setPermission(1);
            success = addUser(registerUser);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean login(String username, String password, HttpSession session) {
        if(getUserByUsername(username) == null){
            throw new RuntimeException("该用户不存在！");
        }else{
            if(password.equals(userDao.queryPasswordByUsername(username))){
                session.setAttribute("user", username);
                logger.info("设置的session:" + session.getAttribute("user"));
                return true;
            }else {
                throw new RuntimeException("输入的密码不正确！");
            }
        }
    }


}
