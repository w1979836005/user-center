package com.gaoqiao.usercenterbacked.service;

import com.gaoqiao.usercenterbacked.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 19798
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-12-12 14:26:07
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 注册成功用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request 请求
     * @return 脱敏用户
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 用户登出
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);


    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    User getCurrentUser(HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User getSafetyUser(User user);


    /**
     * 用户搜索
     * @param username
     * @return
     */
    List<User> searchUsers(String username);
}
