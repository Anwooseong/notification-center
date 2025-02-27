package com.ws.task;

import com.ws.client.CommentClient;
import com.ws.client.PostClient;
import com.ws.domain.NotificationType;
import com.ws.domain.Post;
import com.ws.event.CommentEvent;
import com.ws.service.NotificationGetService;
import com.ws.service.NotificationRemoveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentRemoveTask {

    private final PostClient postClient;
    private final CommentClient commentClient;
    private final NotificationGetService notificationGetService;
    private final NotificationRemoveService notificationRemoveService;

    public void processEvent(CommentEvent event) {
        // 자신의 댓글인 경우 무시
        Post post = postClient.getPost(event.getPostId());
        if (Objects.equals(post.getUserId(), event.getUserId())) {
            return;
        }

        notificationGetService.getNotificationByTypeAndCommentId(NotificationType.COMMENT, event.getCommentId())
                .ifPresentOrElse(
                        notification -> notificationRemoveService.deleteById(notification.getId()),
                        () -> log.error("Notification not found")
                );

    }
}
