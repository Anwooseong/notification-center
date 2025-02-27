package com.ws.task;

import com.ws.*;
import com.ws.event.LikeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeAddTask {

    private final PostClient postClient;
    private final NotificationGetService notificationGetService;
    private final NotificationSaveService notificationSaveService;

    public void processEvent(LikeEvent event) {
        // 자신의 게시글인 경우 무시
        Post post = postClient.getPost(event.getPostId());
        if (post == null) {
            log.error("Post is null with postId:{}", event.getPostId());
            return;
        }

        if (Objects.equals(post.getUserId(), event.getUserId())) {
            return;
        }

        Notification notification = createOrUpdateNotification(event, post);
        notificationSaveService.upsert(notification);
    }

    private Notification createOrUpdateNotification(LikeEvent event, Post post) {
        Optional<Notification> optionalNotification = notificationGetService.getNotificationByTypeAndPostId(NotificationType.LIKE, post.getId());

        Instant now = Instant.now();
        Instant retention = Instant.now().plus(90, ChronoUnit.DAYS);

        if (optionalNotification.isPresent()) {
            // 업데이트
            return updateNotification((LikeNotification) optionalNotification.get(), event, now, retention);
        } else {
            // 신규 생성
            return createNotification(event, post, now, retention);
        }
    }

    private Notification updateNotification(LikeNotification notification, LikeEvent event, Instant now, Instant retention) {
        notification.addLiker(event.getUserId(), event.getCreatedAt(), now, retention);
        return notification;
    }

    private Notification createNotification(LikeEvent event, Post post, Instant now, Instant retention) {
        return new LikeNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(),
                NotificationType.LIKE,
                event.getCreatedAt(),
                now,
                now,
                retention,
                post.getId(),
                List.of(event.getUserId())
        );
    }
}
