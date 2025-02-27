package com.ws;

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
        return notificationRepository.getByTypeAndPostId(type, postId);
    }
}
