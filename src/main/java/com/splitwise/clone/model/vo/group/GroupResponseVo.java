package com.splitwise.clone.model.vo.group;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
public class GroupResponseVo {

    private Long id;

    private String groupName;

    private Instant createdOn;

    private List<Long> memberIds;
}

