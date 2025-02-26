package com.ws;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

enum NotificationType {
    LIKE,
    COMMENT,
    FOLLOW,
}

@Setter
@Getter
@AllArgsConstructor
@Document("notifications")
public class Notification {
    @Field(targetType = FieldType.STRING)   // ObjectId('123') -> "123"
    private String id;

    private Long userId;

    private NotificationType type;

    private Instant createdAt;

    private Instant deletedAt;

}
