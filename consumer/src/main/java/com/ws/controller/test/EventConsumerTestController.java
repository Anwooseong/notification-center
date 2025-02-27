package com.ws.controller.test;

import com.ws.event.CommentEvent;
import com.ws.event.FollowEvent;
import com.ws.event.LikeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
public class EventConsumerTestController implements EventConsumerTestControllerSpec{

    private final Consumer<CommentEvent> commentEventConsumer;
    private final Consumer<LikeEvent> likeEventConsumer;
    private final Consumer<FollowEvent> followEventConsumer;


    @Override
    @PostMapping("/test/comment")
    public void comment(@RequestBody CommentEvent event) {
        commentEventConsumer.accept(event);
    }

    @Override
    @PostMapping("/test/like")
    public void like(@RequestBody LikeEvent event) {
        likeEventConsumer.accept(event);
    }

    @Override
    @PostMapping("/test/follow")
    public void follow(@RequestBody FollowEvent event) {
        followEventConsumer.accept(event);
    }
}
