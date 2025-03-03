package com.ws.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class NotificationReadRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Instant setLastReadAt(long userId) {
        long lastReadAt = Instant.now().toEpochMilli();
        String key = userId + ":lastReadAt";
        redisTemplate.opsForValue().set(key, String.valueOf(lastReadAt));
        redisTemplate.expire(key, 90, TimeUnit.DAYS); // TTL
        return Instant.ofEpochMilli(lastReadAt);
    }
}
