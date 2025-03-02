package com.ws.service;

import com.ws.domain.Notification;
import com.ws.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NotificationListService {

    private final NotificationRepository notificationRepository;

    public Slice<Notification> getUserNotificationByPivot(Long userId, Instant occurredAt) {
        if (occurredAt == null) {
            return notificationRepository.findAllByUserIdOrderByOccurredAtDesc(userId, PageRequest.of(0, PAGE_SIZE));
        }else {
            return notificationRepository.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, occurredAt, PageRequest.of(0, PAGE_SIZE));
        }
    }

    private static final int PAGE_SIZE = 20;
}
