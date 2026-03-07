package com.splitwise.clone.service.friend.impl;

import com.splitwise.clone.exception.DataValidationException;
import com.splitwise.clone.jpa.entity.FriendEntity;
import com.splitwise.clone.jpa.repository.FriendRepository;
import com.splitwise.clone.model.exception.ValidationError;
import com.splitwise.clone.service.friend.IFriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService implements IFriendService {

    private final FriendRepository friendRepository;

    @Override
    public void addFriend(com.splitwise.clone.model.request.friend.FriendRequest friendRequest, String userEmail) throws DataValidationException {

        var friendEmail = friendRequest.getFriendEmail();

        log.info("Adding friend with email: {} for user: {}", friendEmail, userEmail);

        var friendEntityOptional = friendRepository.findByUserEmailAndFriendEmail(userEmail, friendEmail);
        if (friendEntityOptional.isEmpty()) {
            log.info("No existing friendship found between {} and {}", userEmail, friendEmail);
            var friendEntity = new FriendEntity();
            friendEntity.setUserEmail(userEmail);
            friendEntity.setFriendEmail(friendEmail);
            friendEntity.setCreatedOn(Instant.now());
            friendRepository.save(friendEntity);
            log.info("New friendship created between {} and {}", userEmail, friendEmail);
        } else {
            throw new DataValidationException(ValidationError.builder().name("friendEmail").path("payload").reason("Friend already exist").build());
        }

    }
}
