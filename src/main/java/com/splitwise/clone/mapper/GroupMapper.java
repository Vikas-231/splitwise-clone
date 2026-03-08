package com.splitwise.clone.mapper;

import com.splitwise.clone.jpa.entity.GroupEntity;
import com.splitwise.clone.model.request.group.CreateGroupRequest;
import com.splitwise.clone.model.response.group.GroupResponse;
import com.splitwise.clone.model.vo.group.CreateGroupRequestVo;
import com.splitwise.clone.model.vo.group.GroupResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    CreateGroupRequestVo toCreateGroupRequestVo(CreateGroupRequest createGroupRequest);

    GroupResponseVo toGroupResponseVo(GroupEntity groupEntity);

    GroupResponse toGroupResponse(GroupResponseVo groupResponseVo);

    List<GroupResponseVo> toGroupResponseVoList(List<GroupEntity> groupEntities);

    List<GroupResponse> toGroupResponseList(List<GroupResponseVo> groupResponseVoList);
}
