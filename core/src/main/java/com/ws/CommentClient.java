package com.ws;

import org.springframework.stereotype.Component;

@Component
public interface CommentClient {
    Comment getComment(Long id);
}
