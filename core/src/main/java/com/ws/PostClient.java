package com.ws;

import org.springframework.stereotype.Component;

@Component
public interface PostClient {
    Post getPost(Long id);
}
