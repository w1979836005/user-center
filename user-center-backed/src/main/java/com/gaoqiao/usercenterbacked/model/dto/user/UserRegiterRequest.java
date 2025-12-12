package com.gaoqiao.usercenterbacked.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegiterRequest implements Serializable {
    private String userAccount;
    private String userPassword;
    private String checkPassword;

    private static final long serialVersionUID = 1L;
}
