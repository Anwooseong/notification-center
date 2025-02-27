package com.ws.task;

import com.ws.domain.FollowNotification;
import com.ws.utils.NotificationIdGenerator;
import com.ws.service.NotificationSaveService;
import com.ws.domain.NotificationType;
import com.ws.event.FollowEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class FollowAddTask {

    private final NotificationSaveService notificationSaveService;

    public void processEvent(FollowEvent event) {
        notificationSaveService.insert(createFollowNotification(event));
    }

    @NotNull
    private static FollowNotification createFollowNotification(FollowEvent event) {
        Instant now = Instant.now();
        Instant retention = now.plus(90, ChronoUnit.DAYS);

        return new FollowNotification(
                NotificationIdGenerator.generate(),
                event.getTargetUserId(),
                NotificationType.FOLLOW,
                event.getCreatedAt(),
                now,
                now,
                retention,
                event.getUserId()
        );
    }
}
