package com.ws;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootApplication
@SpringBootTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository sut;

    private final long userId = 2L;
    private final long postId = 3L;
    private final long writerId = 4L;
    private final long commentId = 5L;
    private final String comment = "comment";
    private final Instant now = Instant.now();
    private final Instant ninetyDaysAfter = Instant.now().plus(90, DAYS);

    @DisplayName("알림 저장")
    @Test
    void noti_save() {
        // given
        String id = "1";
        sut.save(createCommentNotification(id));

        // when
        Optional<Notification> findNotification = sut.findById("1");

        // then
        Assertions.assertTrue(findNotification.isPresent());
    }

    @DisplayName("알림 ID 찾기")
    @Test
    void noti_findById() {
        // given
        String id = "2";
        sut.save(createCommentNotification(id));

        // when
        CommentNotification findNotification = (CommentNotification) sut.findById("2").orElseThrow();

        // then
        assertEquals(findNotification.getId(), id);
        assertEquals(findNotification.getUserId(), userId);
        assertEquals(findNotification.getOccurredAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(findNotification.getCreatedAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(findNotification.getLastUpdatedAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(findNotification.getDeletedAt().getEpochSecond(), ninetyDaysAfter.getEpochSecond());
        assertEquals(findNotification.getPostId(), postId);
        assertEquals(findNotification.getWriterId(), writerId);
        assertEquals(findNotification.getComment(), comment);
        assertEquals(findNotification.getCommentId(), commentId);
    }

    @DisplayName("알림 ID 지우기")
    @Test
    void noti_deleteById() {
        // given
        String id = "3";
        sut.save(createCommentNotification(id));



        // when
        sut.deleteById("3");
        Optional<Notification> findNotification = sut.findById("3");

        // then
        Assertions.assertFalse(findNotification.isPresent());
    }

    private CommentNotification createCommentNotification(String id) {
        return new CommentNotification(id, userId, NotificationType.COMMENT, now, now, now, ninetyDaysAfter, postId, writerId, comment,
                commentId);
    }

}