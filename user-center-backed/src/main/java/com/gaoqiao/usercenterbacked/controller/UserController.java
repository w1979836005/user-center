package com.gaoqiao.usercenterbacked.controller;
import com.gaoqiao.usercenterbacked.common.BaseResponse;
import com.gaoqiao.usercenterbacked.common.ErrorCode;
import com.gaoqiao.usercenterbacked.common.ResultUtils;
import com.gaoqiao.usercenterbacked.exception.BusinessException;
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
    public BaseResponse<Long> userRegister(@RequestBody UserRegiterRequest userRegiterRequest){

        if(userRegiterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userRegiterRequest.getUserAccount();
        String userPassword = userRegiterRequest.getUserPassword();
        String checkPassword = userRegiterRequest.getCheckPassword();

        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }


        long result = userService.userRegister(userAccount, userPassword, checkPassword);

        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if(userLoginRequest == null)  {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        User user = userService.doLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }
}
