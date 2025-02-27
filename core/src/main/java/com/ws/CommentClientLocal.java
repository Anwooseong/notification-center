package com.ws;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommentClientLocal implements CommentClient {

    private final Map<Long, Comment> comments = new ConcurrentHashMap<>();

    public CommentClientLocal() {
        comments.put(1L, new Comment(1L,  "content1", Instant.now()));
        comments.put(2L, new Comment(2L,  "content2", Instant.now()));
        comments.put(3L, new Comment(3L,  "content3", Instant.now()));
    }

    @Override
    public Comment getComment(Long id) {
        return comments.get(id);
    }
}
