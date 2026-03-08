package com.splitwise.clone.model.response.friend;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class FriendResponseVo {

    private String friendEmail;

    private Instant createdOn;
}
