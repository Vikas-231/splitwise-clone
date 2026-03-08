package com.splitwise.clone.controller;

import com.splitwise.clone.exception.DataValidationException;
import com.splitwise.clone.mapper.GroupMapper;
import com.splitwise.clone.model.request.group.CreateGroupRequest;
import com.splitwise.clone.model.response.group.GroupResponse;
import com.splitwise.clone.model.vo.auth.UserContext;
import com.splitwise.clone.service.group.IGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    private final IGroupService groupService;

    @PostMapping("/create")
    public void createGroup(@Valid @RequestBody CreateGroupRequest createGroupRequest) {
        log.info("Create group request received");

        var createGroupRequestVo = GroupMapper.INSTANCE.toCreateGroupRequestVo(createGroupRequest);

        UserContext userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        groupService.createGroup(createGroupRequestVo,userContext.getUserId());

        log.info("Create group request sent");
    }

    @GetMapping()
    public ResponseEntity<List<GroupResponse>> getGroups() {
        log.info("Get groups request received");

        var userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var groupResponseVoList = groupService.getGroups(userContext.getUserId());

        log.info("Successfully fetched {} groups", groupResponseVoList.size());
        return ResponseEntity.ok(GroupMapper.INSTANCE.toGroupResponseList(groupResponseVoList));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroups(@PathVariable Long groupId) throws DataValidationException {
        log.info("Get group request received");

        var userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var groupResponseVo = groupService.getGroup(userContext.getUserId(), groupId);

        log.info("Successfully fetched {} group", groupResponseVo.getGroupName());
        return ResponseEntity.ok(GroupMapper.INSTANCE.toGroupResponse(groupResponseVo));
    }
}
