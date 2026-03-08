package com.splitwise.clone.service.friend;

import com.splitwise.clone.exception.DataValidationException;
import com.splitwise.clone.model.request.friend.AddFriendRequest;
import com.splitwise.clone.model.response.friend.FriendResponseVo;

import java.util.List;

public interface IFriendService {

    void addFriend(String friendEmail, String userEmail) throws DataValidationException;

    List<FriendResponseVo> getFriends(String userEmail);
}
