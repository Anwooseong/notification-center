package com.ws.service;

import com.ws.domain.CommentNotification;
import com.ws.domain.FollowNotification;
import com.ws.domain.LikeNotification;
import com.ws.service.convertor.CommentUserNotificationConvertor;
import com.ws.service.convertor.FollowUserNotificationConvertor;
import com.ws.service.convertor.LikeUserNotificationConvertor;
import com.ws.service.dto.ConvertedNotification;
import com.ws.service.dto.GetUserNotificationByPivotResult;
import com.ws.service.dto.GetUserNotificationsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetUserNotificationService {

    private final NotificationListService notificationListService;
    private final CommentUserNotificationConvertor commentConvertor;
    private final LikeUserNotificationConvertor likeConvertor;
    private final FollowUserNotificationConvertor followConvertor;

    public GetUserNotificationsResult getUserNotificationsByPivot(Long userId, Instant pivot) {
        GetUserNotificationByPivotResult result = notificationListService.getUserNotificationByPivot(userId, pivot);

        List<ConvertedNotification> convertedNotifications = result.getNotifications().stream()
                .map(notification -> switch (notification.getType()) {
                    case COMMENT -> commentConvertor.convert((CommentNotification) notification);
                    case FOLLOW -> followConvertor.convert((FollowNotification) notification);
                    case LIKE -> likeConvertor.convert((LikeNotification) notification);
                }).toList();

        return new GetUserNotificationsResult(
                convertedNotifications,
                result.isHasNext()
        );
    }

}
