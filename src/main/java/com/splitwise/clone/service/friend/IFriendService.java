package com.splitwise.clone.service.friend;

import com.splitwise.clone.exception.DataValidationException;
import com.splitwise.clone.model.request.friend.FriendRequest;

public interface IFriendService {

    void addFriend(FriendRequest friendRequest, String userEmail) throws DataValidationException;
}
