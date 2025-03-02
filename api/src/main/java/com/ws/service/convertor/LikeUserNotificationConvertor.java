package com.ws.service.convertor;

import com.ws.client.PostClient;
import com.ws.client.UserClient;
import com.ws.domain.CommentNotification;
import com.ws.domain.LikeNotification;
import com.ws.domain.Post;
import com.ws.domain.User;
import com.ws.service.dto.ConvertedCommentNotification;
import com.ws.service.dto.ConvertedLikeNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeUserNotificationConvertor {

    private final UserClient userClient;
    private final PostClient postClient;

    public ConvertedLikeNotification convert(LikeNotification notification) {
        User user = userClient.getUser(notification.getLikerIds().getLast());
        Post post = postClient.getPost(notification.getPostId());

        return new ConvertedLikeNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),
                notification.getLikerIds().size(),
                post.getImageUrl()
        );
    }
}
