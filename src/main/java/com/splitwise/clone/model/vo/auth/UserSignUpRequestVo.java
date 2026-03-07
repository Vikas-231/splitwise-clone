package com.splitwise.clone.model.vo.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequestVo {

    private String userName;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Long phoneNumber;
}

