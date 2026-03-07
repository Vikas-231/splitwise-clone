package com.splitwise.clone.jpa.repository;

import com.splitwise.clone.jpa.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {

    Optional<FriendEntity> findByUserEmailAndFriendEmail(String userEmail, String friendEmail);
}
