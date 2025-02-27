//package com.ws;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Optional;
//
//import static java.time.temporal.ChronoUnit.DAYS;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootApplication
//@SpringBootTest
//class NotificationRepositoryTest {
//
//    @Autowired
//    private NotificationRepository sut;
//
//    private final long userId = 2L;
//    private final long postId = 3L;
//    private final long writerId = 4L;
//    private final long commentId = 5L;
//    private final String comment = "comment";
//    private final Instant now = Instant.now();
//    private final Instant ninetyDaysAfter = Instant.now().plus(90, DAYS);
//
//    @DisplayName("알림 저장")
//    @Test
//    void noti_save() {
//        // given
//        CommentNotification notification = new CommentNotification("1", 2L, NotificationType.LIKE, Instant.now(), Instant.now().plus(90, ChronoUnit.DAYS));
//
//        // when
//        sut.save(notification);
//        Optional<Notification> findNotification = sut.findById("1");
//
//        // then
//        Assertions.assertTrue(findNotification.isPresent());
//    }
//
//    @DisplayName("알림 ID 찾기")
//    @Test
//    void noti_findById() {
//        // given
//        Notification notification = new Notification("2", userId, NotificationType.LIKE, now, ninetyDaysAfter);
//        sut.save(notification);
//
//        // when
//        Notification findNotification = sut.findById("2").orElseThrow();
//
//        // then
//        Assertions.assertEquals(findNotification.getId(), "2");
//        Assertions.assertEquals(findNotification.getUserId(), 2L);
//        Assertions.assertEquals(findNotification.getCreatedAt().getEpochSecond(), now.getEpochSecond());
//        Assertions.assertEquals(findNotification.getDeletedAt().getEpochSecond(), ninetyDaysAfter.getEpochSecond());
//    }
//
//    @DisplayName("알림 ID 지우기")
//    @Test
//    void noti_deleteById() {
//        // given
//        Notification notification = new Notification("3", 2L, NotificationType.LIKE, Instant.now(), Instant.now().plus(90, ChronoUnit.DAYS));
//        sut.save(notification);
//
//        // when
//        sut.deleteById("3");
//        Optional<Notification> findNotification = sut.findById("3");
//
//        // then
//        Assertions.assertFalse(findNotification.isPresent());
//    }
//
//}