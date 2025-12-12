package com.gaoqiao.usercenterbacked.controller;
import com.gaoqiao.usercenterbacked.model.domain.User;
import com.gaoqiao.usercenterbacked.model.dto.user.UserLoginRequest;
import com.gaoqiao.usercenterbacked.model.dto.user.UserRegiterRequest;
import com.gaoqiao.usercenterbacked.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegiterRequest userRegiterRequest){

        if(userRegiterRequest == null) return null;

        String userAccount = userRegiterRequest.getUserAccount();
        String userPassword = userRegiterRequest.getUserPassword();
        String checkPassword = userRegiterRequest.getCheckPassword();

        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return null;
        }
        return userService.userRegister(userAccount,userPassword,checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if(userLoginRequest == null)  {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        return userService.doLogin(userAccount,userPassword, request);
    }
}
