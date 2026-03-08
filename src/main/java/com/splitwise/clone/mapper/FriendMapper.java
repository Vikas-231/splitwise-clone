package com.splitwise.clone.mapper;

import com.splitwise.clone.jpa.entity.FriendEntity;
import com.splitwise.clone.model.response.friend.FriendResponse;
import com.splitwise.clone.model.response.friend.FriendResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendMapper {

    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);

    FriendResponseVo toFriendsResponseVo(FriendEntity friendEntity);

    List<FriendResponseVo> toFriendResponseVoList(List<FriendEntity> friendEntities);

    List<FriendResponse> toFriendsResponseList(List<FriendResponseVo> friendResponseVoList);
}

