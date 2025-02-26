package com.ws.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class CommentEventConsumer {

    @Bean(name = "comment")
    public Consumer<CommentEvent> comment() {  //application-event.yml에 등록된 function definition에 따라서 함수명 설정
        return event -> log.info(event.toString());
    }
}
