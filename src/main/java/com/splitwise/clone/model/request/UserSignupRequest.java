package com.splitwise.clone.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.splitwise.clone.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {

    @NotBlank
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$")
    @JsonProperty("userName")
    private String userName;

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    @StrongPassword
    @JsonProperty("password")
    private String password;

    @NotBlank
    @Size(min = 3, max = 20)
    @JsonProperty("firstName")
    private String firstName;

    @Size(min = 3, max = 20)
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phoneNumber")
    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;
}
