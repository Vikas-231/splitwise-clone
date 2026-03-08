package com.splitwise.clone.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "group_members")
@Getter
@Setter
public class GroupMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "active_flag", nullable = false)
    private Integer activeFlag;

    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    @Column(name = "modified_on", nullable = false)
    private Instant modifiedOn;
}

