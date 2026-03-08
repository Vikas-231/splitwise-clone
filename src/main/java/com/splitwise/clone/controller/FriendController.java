package com.splitwise.clone.controller;

import com.splitwise.clone.exception.DataValidationException;
import com.splitwise.clone.mapper.FriendMapper;
import com.splitwise.clone.model.request.friend.AddFriendRequest;
import com.splitwise.clone.model.response.friend.FriendResponse;
import com.splitwise.clone.model.vo.auth.UserContext;
import com.splitwise.clone.service.friend.IFriendService;
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
@RequestMapping("/friends")
public class FriendController {

    private final IFriendService friendService;

    @PostMapping("/add")
    public void addFriend(@Valid @RequestBody AddFriendRequest addFriendRequest) throws DataValidationException {
        log.info("Add friend request received");

        var userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        friendService.addFriend(addFriendRequest.getFriendEmail(), userContext.getEmail());

        log.info("Add friend request sent");

    }

    @GetMapping
    public ResponseEntity<List<FriendResponse>> getFriends() {
        log.info("Get friends request received");

        var userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var friendsResponseVoList = friendService.getFriends(userContext.getEmail());

        log.info("Successfully fetched {} friends", friendsResponseVoList.size());
        return ResponseEntity.ok(FriendMapper.INSTANCE.toFriendsResponseList(friendsResponseVoList));
    }

}
