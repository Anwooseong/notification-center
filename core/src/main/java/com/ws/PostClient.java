package com.ws;

import org.springframework.stereotype.Component;

public interface PostClient {
    Post getPost(Long id);
}
