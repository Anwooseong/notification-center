package com.ws.repository;

import com.ws.IntegrationTest;
import com.ws.domain.CommentNotification;
import com.ws.domain.Notification;
import com.ws.domain.NotificationType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.Instant;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationRepositoryTest extends IntegrationTest {

    @Autowired
    private NotificationRepository sut;

    private final long userId = 2L;
    private final long postId = 3L;
    private final long writerId = 4L;
    private final long commentId = 5L;
    private final String comment = "comment";
    private final Instant now = Instant.now();
    private final Instant ninetyDaysAfter = Instant.now().plus(90, DAYS);

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 5; i++) {
            Instant occurredAt = now.minus(i, DAYS);
            sut.save(new CommentNotification(
                    "id-"+i,
                    userId,
                    NotificationType.COMMENT,
                    occurredAt,
                    now,
                    now,
                    ninetyDaysAfter,
                    postId,
                    writerId,
                    comment,
                    commentId
            ));
        }
    }

    @AfterEach
    void tearDown() {
        sut.deleteAll();
    }

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

    @DisplayName("첫 알림조회시 occurredAt 내림차순 3개 나열")
    @Test
    void findAllByUserIdOrderByOccurredAtDesc() {
        // given
        Pageable pageable = PageRequest.of(0, 3);

        // when
        Slice<Notification> result = sut.findAllByUserIdOrderByOccurredAtDesc(userId, pageable);

        // then
        org.assertj.core.api.Assertions.assertThat(result.getContent().size()).isEqualTo(3);
        org.assertj.core.api.Assertions.assertThat(result.hasNext()).isTrue();
    }

    @DisplayName("첫 알림조회시 occurredAt 내림차순인지 확인")
    @Test
    void findAllByUserIdOrderByOccurredAtDesc2() {
        // given
        Pageable pageable = PageRequest.of(0, 3);

        // when
        Slice<Notification> result = sut.findAllByUserIdOrderByOccurredAtDesc(userId, pageable);
        Notification firstNotification = result.getContent().get(0);
        Notification secontNotification = result.getContent().get(1);
        Notification thirdNotification = result.getContent().get(2);

        // then
        org.assertj.core.api.Assertions.assertThat(firstNotification.getOccurredAt()).isAfter(secontNotification.getOccurredAt());
        org.assertj.core.api.Assertions.assertThat(secontNotification.getOccurredAt()).isAfter(thirdNotification.getOccurredAt());
    }

    @DisplayName("매개변수 occurredAt 이후의 알림 pivot 없을때")
    @Test
    void findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc_NotPivot() {
        // given
        Pageable pageable = PageRequest.of(0, 3);

        // when
        Slice<Notification> result = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, now, pageable);
        Notification firstNotification = result.getContent().get(0);
        Notification secontNotification = result.getContent().get(1);
        Notification thirdNotification = result.getContent().get(2);

        // then
        org.assertj.core.api.Assertions.assertThat(result.getContent().size()).isEqualTo(3);
        org.assertj.core.api.Assertions.assertThat(result.hasNext()).isTrue();
        org.assertj.core.api.Assertions.assertThat(firstNotification.getOccurredAt()).isAfter(secontNotification.getOccurredAt());
        org.assertj.core.api.Assertions.assertThat(secontNotification.getOccurredAt()).isAfter(thirdNotification.getOccurredAt());
    }

    @DisplayName("매개변수 occurredAt 이후의 알림 pivot")
    @Test
    void findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc_WithPivot() {
        // given
        Pageable pageable = PageRequest.of(0, 3);

        // when
        Slice<Notification> firstResult = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, now, pageable);
        Notification last = firstResult.getContent().get(2);
        Instant pivot = last.getOccurredAt();

        Slice<Notification> secondResult = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, pivot, pageable);
        Notification firstNotification = secondResult.getContent().get(0);
        Notification secondNotification = secondResult.getContent().get(1);

        // then
        org.assertj.core.api.Assertions.assertThat(secondResult.hasNext()).isFalse();
        org.assertj.core.api.Assertions.assertThat(secondResult.getContent().size()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(firstNotification.getOccurredAt()).isAfter(secondNotification.getOccurredAt());
    }

    private CommentNotification createCommentNotification(String id) {
        return new CommentNotification(id, userId, NotificationType.COMMENT, now, now, now, ninetyDaysAfter, postId, writerId, comment,
                commentId);
    }

}