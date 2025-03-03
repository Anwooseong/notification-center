package com.ws.service;

import com.ws.repository.NotificationReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LastReadAtService {

    private final NotificationReadRepository notificationReadRepository;

    public Instant setLastReadAt(long userId) {
        return notificationReadRepository.setLastReadAt(userId);
    }
}
