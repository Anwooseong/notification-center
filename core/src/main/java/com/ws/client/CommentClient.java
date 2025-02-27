package com.ws.client;

import com.ws.domain.Comment;

public interface CommentClient {
    Comment getComment(Long id);
}
