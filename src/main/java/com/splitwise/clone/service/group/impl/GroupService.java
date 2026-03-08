package com.splitwise.clone.service.group.impl;

import com.splitwise.clone.exception.DataValidationException;
import com.splitwise.clone.jpa.entity.GroupEntity;
import com.splitwise.clone.jpa.entity.GroupMemberEntity;
import com.splitwise.clone.jpa.repository.GroupMemberRepository;
import com.splitwise.clone.jpa.repository.GroupRepository;
import com.splitwise.clone.mapper.GroupMapper;
import com.splitwise.clone.model.exception.ValidationError;
import com.splitwise.clone.model.vo.group.CreateGroupRequestVo;
import com.splitwise.clone.model.vo.group.GroupResponseVo;
import com.splitwise.clone.service.group.IGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService implements IGroupService {

    private final GroupRepository groupRepository;

    private final GroupMemberRepository groupMemberRepository;

    @Override
    public void createGroup(CreateGroupRequestVo createGroupRequestVo, Long userId) {

        groupRepository.findByGroupNameAndCreatedBy(createGroupRequestVo.getGroupName(), userId).ifPresent(group -> {
            log.error("Group with name {} already exists", createGroupRequestVo.getGroupName());
            throw new IllegalArgumentException("Group with name " + createGroupRequestVo.getGroupName() + " already exists for this user");
        });

        var groupEntity = getGroupEntity(createGroupRequestVo, userId);

        var savedEntity = groupRepository.save(groupEntity);

        var groupMemberEntityList = getGroupMemberEntity(savedEntity.getId(), createGroupRequestVo.getUserIds());

        groupMemberRepository.saveAll(groupMemberEntityList);
    }

    @Override
    public List<GroupResponseVo> getGroups(Long userId) {
        var groupEntities = groupRepository.findAllByCreatedBy(userId);
        List<GroupResponseVo> groupResponseVoList = GroupMapper.INSTANCE.toGroupResponseVoList(groupEntities);

        List<Long> groupIds = groupResponseVoList.stream().map(GroupResponseVo::getId).toList();
        Map<Long, List<Long>> groupMembersMap = groupMemberRepository.findByGroupIdIn(groupIds).stream()
                .collect(Collectors.groupingBy(
                        entity -> entity.getGroupId(),
                        Collectors.mapping(entity -> entity.getMemberId(), Collectors.toList())
                ));

        groupResponseVoList.forEach(groupResponse ->
                groupResponse.setMemberIds(groupMembersMap.getOrDefault(groupResponse.getId(), Collections.emptyList()))
        );

        return groupResponseVoList;
    }

    @Override
    public GroupResponseVo getGroup(Long userId, Long groupId) throws DataValidationException {
        var groupEntity = groupRepository.findByIdAndCreatedBy(groupId, userId).orElseThrow(() -> new DataValidationException(ValidationError.builder().name("groupId").path("pathParam").reason("Invalid group id").build()));

        var groupResponseVo = GroupMapper.INSTANCE.toGroupResponseVo(groupEntity);

        var groupMembersList = groupMemberRepository.findByGroupId(groupId);
        groupResponseVo.setMemberIds(groupMembersList.stream().map((groupMemberEntity) -> groupMemberEntity.getMemberId()).toList());
        return groupResponseVo;
    }

    private List<GroupMemberEntity> getGroupMemberEntity(Long id, List<Long> userIds) {
        var groupMemberEntities = userIds.stream().map(userId -> {
            var groupMemberEntity = new GroupMemberEntity();
            groupMemberEntity.setGroupId(id);
            groupMemberEntity.setCreatedOn(Instant.now());
            groupMemberEntity.setMemberId(userId);
            groupMemberEntity.setActiveFlag(1);
            return groupMemberEntity;
        }).toList();
        return groupMemberEntities;
    }

    private GroupEntity getGroupEntity(CreateGroupRequestVo createGroupRequestVo, Long userId) {
        var groupEntity = new GroupEntity();
        groupEntity.setCreatedBy(userId);
        groupEntity.setGroupName(createGroupRequestVo.getGroupName());
        groupEntity.setCreatedOn(Instant.now());
        return groupEntity;
    }


}
