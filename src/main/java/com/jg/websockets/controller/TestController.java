package com.jg.websockets.controller;

import com.jg.websockets.model.Greeting;
import com.jg.websockets.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final SimpMessagingTemplate template;

    @SneakyThrows
    @PostMapping("/hello")
    public void greeting(@RequestBody final HelloMessage helloMessage) {
        log.info("Received message: {}", helloMessage);
        Thread.sleep(1000);
        template.convertAndSend("/topic/greetings", new Greeting("Hello " + helloMessage.getName() + "!"));
    }

}
