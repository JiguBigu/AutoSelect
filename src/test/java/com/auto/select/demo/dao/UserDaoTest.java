package com.auto.select.demo.dao;

import com.auto.select.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/7 17:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void insertUser(){
        User user1 = new User();
        user1.setUsername("admin33");
        user1.setPassword("123456");
        user1.setPermission(1);
        user1.setCreateTime(new Date());
        user1.setLastEditTime(new Date());
        int effectNum = userDao.insertUser(user1);
        assertEquals(1, effectNum);
    }

    @Test
    public void queryUserByUsername(){
        User user = userDao.queryUserByUsername("jigubigu2");
        System.out.println(user);
    }

    @Test
    public void updateUser(){
        User user2 = new User();
        user2.setPermission(2);
        user2.setUsername("jigubigu2");
        int effectNum = userDao.updateUser(user2);
        assertEquals(1,effectNum);
    }

    @Test
    public void queryPasswordByUsername(){
        String username = "jigubigu2";
        String password = userDao.queryPasswordByUsername(username);
        System.out.println(password);
        assertEquals("123456", password);

    }
}