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

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final SimpMessagingTemplate template;

    @PostMapping("/hello")
    public String greeting(@RequestBody final HelloMessage helloMessage) {
        log.info("Received message: {}", helloMessage);
        final UUID uuid = UUID.randomUUID();
        final String queue = "/queues/" + uuid;
        new Thread(() -> publishToQueue(queue, helloMessage.getName())).start();
        return queue;
    }

    @SneakyThrows
    private void publishToQueue(final String queue, final String name) {
        Thread.sleep(1000);
        log.info("Publishing to {}.", queue);
        template.convertAndSend(queue, new Greeting("Hello " + name + "!"));
    }

}
