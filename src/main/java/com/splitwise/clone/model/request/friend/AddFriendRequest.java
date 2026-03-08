package com.splitwise.clone.model.request.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFriendRequest {

    @Email
    @NotBlank
    @JsonProperty("friendEmail")
    private String friendEmail;
}
