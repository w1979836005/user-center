package com.gaoqiao.usercenterbacked.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoqiao.usercenterbacked.model.domain.User;
import com.gaoqiao.usercenterbacked.service.UserService;
import com.gaoqiao.usercenterbacked.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
            return -1;
        }

        if(userAccount.length() < 4) {
            return -1;
        }

        if(userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }

        //密码是否相同
        if(!userPassword.equals(checkPassword)) {
            return -1;
        }

        //账户不可以重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);

        if(count > 0) {
            return -1;
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
            return -1;
        }

        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {

        if(StringUtils.isAnyBlank(userAccount,userPassword)) {
            return null;
        }

        if(userAccount.length() < 4) {
            return null;
        }

        if(userPassword.length() < 8 ) {
            return null;
        }

        String newPassword = DigestUtils.md5DigestAsHex((SLAT+userPassword).getBytes());
        //查询比对
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",newPassword);

        User user = userMapper.selectOne(queryWrapper);

        if(user == null) {
            log.info("user is null, maybe no user or userPassword error");
            return null;
        }

        //记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,user);

        //用户脱敏

        return user;
    }
}




