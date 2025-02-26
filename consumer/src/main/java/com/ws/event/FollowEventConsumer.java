package com.ws.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class FollowEventConsumer {

    @Bean(name = "follow")
    public Consumer<FollowEvent> like() {  //application-event.yml 에 등록된 function definition 에 따라서 함수명 설정
        return event -> log.info(event.toString());
    }
}
