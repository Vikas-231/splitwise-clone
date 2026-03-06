package com.splitwise.clone.service.auth.impl;

import com.splitwise.clone.jpa.entity.UserEntity;
import com.splitwise.clone.jpa.repository.UserRepository;
import com.splitwise.clone.mapper.UserMapper;
import com.splitwise.clone.model.vo.UserSignUpRequestVo;
import com.splitwise.clone.service.auth.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserSignUpRequestVo userSignUpRequestVo) {
        UserEntity userEntity = prepareUserDetails(userSignUpRequestVo);
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    private UserEntity prepareUserDetails(UserSignUpRequestVo userSignUpRequestVo) {
        UserEntity userEntity = UserMapper.INSTANCE.toUserEntity(userSignUpRequestVo);
        userEntity.setPassword(passwordEncoder.encode(userSignUpRequestVo.getPassword()));
        userEntity.setStatus(1);
        userEntity.setCreatedOn(Instant.now());
        userEntity.setUpdatedOn(Instant.now());
        return userEntity;
    }
}
