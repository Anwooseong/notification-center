package com.ws.client;

import com.ws.domain.User;

public interface UserClient {

    User getUser(long id);

    Boolean getIsFollowing(long followerId, long followedId);
}
