package com.splitwise.clone.model.auth;

import lombok.Builder;

import java.util.List;

@Builder
public class UserContext {

    private Long userId;

    private String userName;

    private String email;

    private List<String> roles;
}
