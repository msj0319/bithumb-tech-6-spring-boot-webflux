package net.msj0319.api.quiz.controller;

import lombok.RequiredArgsConstructor;
import net.msj0319.api.quiz.config.KafkaProducer;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private final KafkaProducer producer;

    @GetMapping
    public String hello() {
        return "Hello Kafka";
    }

    @PostMapping
    public String sendMessage(@RequestParam("message") String message) {
        System.out.println("############### Producer 진입 ###############");
        this.producer.sendMessage(message);
        return "Message Sent to Kafka Successfully";
    }

}
