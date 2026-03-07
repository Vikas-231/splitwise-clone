package com.splitwise.clone.service.user;

import com.splitwise.clone.model.response.user.UserDetailsResponse;
import com.splitwise.clone.model.response.user.UserSearchResponse;
import com.splitwise.clone.model.vo.auth.UserContext;

public interface IUserService {

    UserDetailsResponse getUserDetails(Long userId, UserContext userContext);

    UserSearchResponse getUserSearchResponse(String searchEmail);
}

