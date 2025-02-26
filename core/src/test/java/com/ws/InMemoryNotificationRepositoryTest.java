package com.ws;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryNotificationRepositoryTest {

    private final InMemoryNotificationRepository sut = new InMemoryNotificationRepository();
    
    @DisplayName("알림 저장")
    @Test
    void noti_save() {
        // given
        Notification notification = new Notification("1", 2L, NotificationType.LIKE, Instant.now(), Instant.now().plus(90, ChronoUnit.DAYS));

        // when
        sut.save(notification);
        Optional<Notification> findNotification = sut.findById("1");

        // then
        Assertions.assertTrue(findNotification.isPresent());
    }

    @DisplayName("알림 ID 찾기")
    @Test
    void noti_findById() {
        // given
        Notification notification = new Notification("2", 2L, NotificationType.LIKE, Instant.now(), Instant.now().plus(90, ChronoUnit.DAYS));
        sut.save(notification);

        // when
        Notification findNotification = sut.findById("2").orElseThrow();

        // then
        Assertions.assertEquals(notification, findNotification);
    }

    @DisplayName("알림 ID 지우기")
    @Test
    void noti_deleteById() {
        // given
        Notification notification = new Notification("3", 2L, NotificationType.LIKE, Instant.now(), Instant.now().plus(90, ChronoUnit.DAYS));
        sut.save(notification);

        // when
        sut.deleteById("3");
        Optional<Notification> findNotification = sut.findById("3");

        // then
        Assertions.assertFalse(findNotification.isPresent());
    }

}