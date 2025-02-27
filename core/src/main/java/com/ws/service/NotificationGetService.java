package com.ws.service;

import com.ws.repository.NotificationRepository;
import com.ws.domain.Notification;
import com.ws.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationGetService {

    private final NotificationRepository notificationRepository;

    public Optional<Notification> getNotificationByTypeAndCommentId(NotificationType type, Long commentId) {
        Optional<Notification> notification = notificationRepository.findByTypeAndCommentId(type, commentId);
        log.info("Notification found: {}", notification);
        return notification;
    }

    public Optional<Notification> getNotificationByTypeAndPostId(NotificationType type, Long postId) {
        return notificationRepository.findByTypeAndPostId(type, postId);
    }

    public Optional<Notification> getNotificationByTypeAndUserIdAndFollowerId(NotificationType type, long userId, long followerId) {
        return notificationRepository.findByTypeAndUserIdAndFollowerId(type, userId, followerId);
    }
}
