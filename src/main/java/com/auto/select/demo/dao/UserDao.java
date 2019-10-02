package com.auto.select.demo.dao;

import com.auto.select.demo.entity.User;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/7 16:59
 */
public interface UserDao {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 查询用户
     */
    User queryUserByUsername(String username);

    /**
     * 插入用户
     * @param user 用户
     * @return 判断标志
     */
    int insertUser(User user);

    /**
     * 修改用户信息
     * @param user 用户
     * @return 判断标志
     */
    int updateUser(User user);

    /**
     * 根据用户删除用户
     * @param username 用户名
     * @return 判断标志
     */
    int deleteUser(String username);

    /**
     * 根据输入的用户名查询密码
     * @param username
     * @return
     */
    String queryPasswordByUsername(String username);
}
