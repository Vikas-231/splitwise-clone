package com.splitwise.clone.mapper;

import com.splitwise.clone.jpa.entity.UserEntity;
import com.splitwise.clone.model.request.UserSignupRequest;
import com.splitwise.clone.model.vo.UserSignUpRequestVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserSignUpRequestVo toUserSignupRequestVo(UserSignupRequest userSignupRequest);

    UserEntity toUserEntity(UserSignUpRequestVo userSignUpRequestVo);
}
