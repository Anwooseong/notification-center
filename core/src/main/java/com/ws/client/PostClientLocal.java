package com.ws.client;

import com.ws.domain.Post;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PostClientLocal implements PostClient {

    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    public PostClientLocal() {
        posts.put(1L, new Post(1L, 111L, "imageUrl", "content"));
        posts.put(2L, new Post(2L, 222L, "imageUrl2", "content2"));
        posts.put(3L, new Post(3L, 333L, "imageUrl3", "content3"));
    }

    @Override
    public Post getPost(Long id) {
        return posts.get(id);
    }
}
