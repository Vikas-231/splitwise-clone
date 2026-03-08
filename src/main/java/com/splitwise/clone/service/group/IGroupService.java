package com.splitwise.clone.service.group;

import com.splitwise.clone.exception.DataValidationException;
import com.splitwise.clone.model.vo.group.CreateGroupRequestVo;
import com.splitwise.clone.model.vo.group.GroupResponseVo;

import java.util.List;

public interface IGroupService {

    void createGroup(CreateGroupRequestVo createGroupRequestVo, Long userId);

    List<GroupResponseVo> getGroups(Long userId);

    GroupResponseVo getGroup(Long userId,Long groupId) throws DataValidationException;
}
