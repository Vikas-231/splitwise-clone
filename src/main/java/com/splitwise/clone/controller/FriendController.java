package com.splitwise.clone.controller;

import com.splitwise.clone.exception.DataValidationException;
import com.splitwise.clone.model.request.friend.FriendRequest;
import com.splitwise.clone.model.vo.auth.UserContext;
import com.splitwise.clone.service.friend.IFriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final IFriendService friendService;

    @PostMapping("/add")
    public void addFriend(@Valid @RequestBody FriendRequest friendRequest) throws DataValidationException {
        log.info("Add friend request received");

        var userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        friendService.addFriend(friendRequest, userContext.getEmail());

        log.info("Add friend request sent");

    }
}
