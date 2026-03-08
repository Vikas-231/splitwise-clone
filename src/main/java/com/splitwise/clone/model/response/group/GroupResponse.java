package com.splitwise.clone.model.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
public class GroupResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("createdOn")
    private Instant createdOn;

    @JsonProperty("memberIds")
    private List<Long> memberIds;
}

