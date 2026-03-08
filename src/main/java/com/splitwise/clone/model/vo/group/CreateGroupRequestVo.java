package com.splitwise.clone.model.vo.group;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGroupRequestVo {

    private String groupName;

    private List<Long> userIds;
}
