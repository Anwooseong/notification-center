package com.ws;

import org.springframework.stereotype.Component;

public interface CommentClient {
    Comment getComment(Long id);
}
