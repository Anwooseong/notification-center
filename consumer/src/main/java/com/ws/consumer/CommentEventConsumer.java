package com.ws.consumer;

import com.ws.event.CommentEvent;
import com.ws.event.CommentEventType;
import com.ws.task.CommentAddTask;
import com.ws.task.CommentRemoveTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentEventConsumer {

    private final CommentAddTask commentAddTask;
    private final CommentRemoveTask commentRemoveTask;

    @Bean(name = "comment")
    public Consumer<CommentEvent> comment() {  //application-event.yml에 등록된 function definition에 따라서 함수명 설정
        return event -> {
            if (event.getType() == CommentEventType.ADD) {
                commentAddTask.processEvent(event);
            } else if (event.getType() == CommentEventType.REMOVE) {
                commentRemoveTask.processEvent(event);
            }
        };
    }
}
