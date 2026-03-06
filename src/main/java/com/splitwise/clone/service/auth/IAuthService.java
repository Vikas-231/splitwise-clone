package com.splitwise.clone.service.auth;

import com.splitwise.clone.jpa.entity.UserEntity;
import com.splitwise.clone.model.vo.UserSignUpRequestVo;

public interface IAuthService {

    void signUp(UserSignUpRequestVo userSignUpRequestVo);

    UserEntity getUserByEmail(String email);
}
