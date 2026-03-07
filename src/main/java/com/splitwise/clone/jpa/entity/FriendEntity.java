package com.splitwise.clone.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "friends")
@Getter
@Setter
public class FriendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email", nullable = false)
    private String userEmail;


    @Column(name = "friend_email", nullable = false)
    private String friendEmail;

    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

}
