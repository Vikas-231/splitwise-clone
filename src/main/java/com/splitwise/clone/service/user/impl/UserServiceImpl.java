package com.splitwise.clone.service.user.impl;

import com.splitwise.clone.jpa.entity.UserEntity;
import com.splitwise.clone.jpa.repository.UserRepository;
import com.splitwise.clone.mapper.UserMapper;
import com.splitwise.clone.model.response.user.UserDetailsResponse;
import com.splitwise.clone.model.response.user.UserSearchResponse;
import com.splitwise.clone.model.vo.auth.UserContext;
import com.splitwise.clone.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsResponse getUserDetails(Long userId, UserContext userContext) {
        log.info("Fetching user details for userId: {}", userId);

        if (!userId.equals(userContext.getUserId())) {
            log.error("User {} attempted to access details of user {}",
                    userContext.getUserId(), userId);
            throw new AccessDeniedException("You are not authorized to access this user's details");
        }

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with userId: {}", userId);
                    return new UsernameNotFoundException("User not found with id: " + userId);
                });

        log.info("Successfully fetched user details for userId: {}", userId);
        return UserMapper.INSTANCE.toUserDetailsResponse(userEntity);
    }

    @Override
    public UserSearchResponse getUserSearchResponse(String searchEmail) {
        log.info("Searching for users with email containing: {}", searchEmail);

        var userEntities = userRepository.findByEmail(searchEmail).orElseThrow(() -> {
            log.error("User not found with email Id: {}", searchEmail);
            return new UsernameNotFoundException("User not found with email id: " + searchEmail);
        });

        log.info("Successfully searched users with email containing: {}", searchEmail);
        return UserMapper.INSTANCE.toUserSearchResponse(userEntities);
    }
}

