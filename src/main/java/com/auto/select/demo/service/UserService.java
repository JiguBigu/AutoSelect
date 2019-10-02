package com.auto.select.demo.service;

import com.auto.select.demo.entity.User;

import javax.servlet.http.HttpSession;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/7 18:10
 */
public interface UserService {
    /**
     * 通过用户名查询用户
     * @param name 用户名
     * @return 查询到的用户
     */
    User getUserByUsername(String name);

    /**
     * 添加用户
     * @param user 用户
     * @return 成功标志
     */
    boolean addUser(User user);

    /**
     * 修改用户信息
     * @param user 用户
     * @return 成功标志
     */
    boolean modifyUser(User user);

    /**
     *删除用户
     * @param username 用户名
     * @return 成功标志
     */
    boolean deleteUser(String username);

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @return 成功标志
     */
    boolean register(String username, String password);

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 成功标志
     */
    boolean login(String username, String password, HttpSession session);
}
