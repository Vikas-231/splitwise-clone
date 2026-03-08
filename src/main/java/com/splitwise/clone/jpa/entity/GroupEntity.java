package com.splitwise.clone.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "groups")
@Getter
@Setter
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "createdby", nullable = false)
    private Long createdBy;

    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    @Column(name = "modified_on", nullable = false)
    private Instant modifiedOn;
}

