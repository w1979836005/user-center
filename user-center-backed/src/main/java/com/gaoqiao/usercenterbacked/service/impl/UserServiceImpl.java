package com.gaoqiao.usercenterbacked.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoqiao.usercenterbacked.common.ErrorCode;
import com.gaoqiao.usercenterbacked.exception.BusinessException;
import com.gaoqiao.usercenterbacked.model.domain.User;
import com.gaoqiao.usercenterbacked.service.UserService;
import com.gaoqiao.usercenterbacked.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @author 19798
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-12-12 14:26:07
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    private static  final String SLAT = "gaoqiao";

    private static final String USER_LOGIN_STATE = "userLoginState";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

        //校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        if(userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号小于4位");
        }

        if(userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码小于8位");
        }

        //密码是否相同
        if(!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致");
        }

        //账户不可以重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);

        if(count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        //加密密码
        String newPassword = DigestUtils.md5DigestAsHex((SLAT+userPassword).getBytes());
        System.out.println(newPassword);


        //插入用户数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setPassword(newPassword);

        boolean saveResult = this.save(user);
        if(!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "保存失败，系统错误");
        }

        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {

        if(StringUtils.isAnyBlank(userAccount,userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        if(userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号小于4位");
        }

        if(userPassword.length() < 8 ) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码小于8位");
        }

        String newPassword = DigestUtils.md5DigestAsHex((SLAT+userPassword).getBytes());
        //查询比对
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("password",newPassword);

        User user = userMapper.selectOne(queryWrapper);

        if(user == null) {
            log.info("user is null, maybe no user or userPassword error");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码不正确");
        }

        //用户脱敏
        User safetyUser = getSafetyUser(user);

        //记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 0;
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);

        if(userObj == null) {
            return null;
        }

        User user =  (User) userObj;

        //查询数据库用户
        User dbUser =  this.getById(user.getId());

        //用户脱敏
        User safetyUser = getSafetyUser(dbUser);

        return safetyUser;
    }

    @Override
    public User getSafetyUser(User user) {
        //用户脱敏
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setCreateTime(user.getCreateTime());


        return safetyUser;
    }

    @Override
    public List<User> searchUsers(String username) {
        if(username == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",username);
        List<User> userList = userMapper.selectList(queryWrapper);

        //用户脱敏
        List<User> safetyUserList = new ArrayList<>();
        for(User user : userList) {
            safetyUserList.add(getSafetyUser(user));
        }

        return safetyUserList;
    }

}




