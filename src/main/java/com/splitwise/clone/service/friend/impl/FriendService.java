package com.splitwise.clone.service.friend.impl;

import com.splitwise.clone.exception.DataValidationException;
import com.splitwise.clone.jpa.entity.FriendEntity;
import com.splitwise.clone.jpa.repository.FriendRepository;
import com.splitwise.clone.mapper.FriendMapper;
import com.splitwise.clone.model.exception.ValidationError;
import com.splitwise.clone.model.response.friend.FriendResponseVo;
import com.splitwise.clone.service.friend.IFriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService implements IFriendService {

    private final FriendRepository friendRepository;

    @Override
    public void addFriend(String friendEmail, String userEmail) throws DataValidationException {
        log.info("Adding friend with email: {} for user: {}", friendEmail, userEmail);

        if (friendEmail.equals(userEmail)) {
            log.error("User {} attempted to add themselves as a friend", userEmail);
            throw new DataValidationException(ValidationError.builder().name("friendEmail").path("payload").reason("Cannot add yourself as a friend").build());
        }


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
            log.error("Friendship already exists between {} and {}", userEmail, friendEmail);
            throw new DataValidationException(ValidationError.builder().name("friendEmail").path("payload").reason("Friend already exist").build());
        }

    }

    @Override
    public List<FriendResponseVo> getFriends(String userEmail) {
        log.info("Fetching friends for user: {}", userEmail);

        var friendEntities = friendRepository.findByUserEmail(userEmail);

        log.info("Found {} friends for user: {}", friendEntities.size(), userEmail);
        return FriendMapper.INSTANCE.toFriendResponseVoList(friendEntities);
    }
}
