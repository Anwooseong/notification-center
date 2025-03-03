package com.ws.controller;

import com.ws.response.UserNotificationListResponse;
import com.ws.service.GetUserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user/notification")
public class UserNotificationListController implements UserNotificationListControllerSpec {

    private final GetUserNotificationService getUserNotificationService;

    @Override
    @GetMapping("/{userId}")
    public UserNotificationListResponse getNotifications(
            @PathVariable(value = "userId") long userId,
            @RequestParam(value = "pivot", required = false) Instant pivot
    ) {
        return UserNotificationListResponse.of(getUserNotificationService.getUserNotificationsByPivot(userId, pivot));
    }
}
