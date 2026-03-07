package com.splitwise.clone.controller;

import com.splitwise.clone.mapper.UserMapper;
import com.splitwise.clone.model.response.user.UserDetailsResponse;
import com.splitwise.clone.model.response.user.UserSearchResponse;
import com.splitwise.clone.model.vo.auth.UserContext;
import com.splitwise.clone.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@PathVariable Long userId) {
        log.info("Get user details request received for userId: {}", userId);

        var userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var userDetailsResponse = userService.getUserDetails(userId, userContext);

        log.info("Successfully fetched user details for userId: {}", userId);
        return ResponseEntity.ok(userDetailsResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<UserSearchResponse> searchUser(@RequestParam("email") String searchEmail) {
        log.info("Search users request received");

        var userSearchResponse = userService.getUserSearchResponse(searchEmail);

        log.info("Successfully searched users");
        return ResponseEntity.ok(userSearchResponse);
    }
}