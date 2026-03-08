package com.splitwise.clone.model.request.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGroupRequest {

    @NotBlank
    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("userIds")
    private List<Long> userIds;

}
