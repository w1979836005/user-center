package com.gaoqiao.usercenterbacked.service;

import com.gaoqiao.usercenterbacked.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

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

    User doLogin(String userAccount, String userPassword, HttpServletRequest request);
}
