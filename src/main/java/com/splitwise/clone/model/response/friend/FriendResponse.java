package com.splitwise.clone.model.response.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class FriendResponse {

    @JsonProperty("friendEmail")
    private String friendEmail;

    @JsonProperty("createdOn")
    private Instant createdOn;
}

