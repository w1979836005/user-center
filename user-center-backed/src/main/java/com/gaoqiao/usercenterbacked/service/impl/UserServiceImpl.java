package com.gaoqiao.usercenterbacked.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoqiao.usercenterbacked.model.domain.User;
import com.gaoqiao.usercenterbacked.service.UserService;
import com.gaoqiao.usercenterbacked.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 19798
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-12-12 14:09:25
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




