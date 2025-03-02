package com.ws.service.convertor;

import com.ws.client.PostClient;
import com.ws.client.UserClient;
import com.ws.domain.FollowNotification;
import com.ws.domain.LikeNotification;
import com.ws.domain.Post;
import com.ws.domain.User;
import com.ws.service.dto.ConvertedFollowNotification;
import com.ws.service.dto.ConvertedLikeNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowUserNotificationConvertor {

    private final UserClient userClient;
    private final PostClient postClient;

    public ConvertedFollowNotification convert(FollowNotification notification) {
        User user = userClient.getUser(notification.getFollowerId());
        Boolean isFollowing = userClient.getIsFollowing(notification.getUserId(), notification.getFollowerId());

        return new ConvertedFollowNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),
                isFollowing
        );
    }
}
