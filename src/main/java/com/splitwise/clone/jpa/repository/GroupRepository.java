package com.splitwise.clone.jpa.repository;

import com.splitwise.clone.jpa.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByGroupNameAndCreatedBy(String groupName, Long createdBy);

    List<GroupEntity> findAllByCreatedBy(Long createdBy);

    Optional<GroupEntity> findByIdAndCreatedBy(Long groupId, Long createdBy);
}

