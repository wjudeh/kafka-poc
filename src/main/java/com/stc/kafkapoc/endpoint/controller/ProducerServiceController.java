package com.stc.kafkapoc.endpoint.controller;

import com.stc.kafkapoc.service.ProducerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ProducerServiceController {

    private final ProducerService producerService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody String message) {

        producerService.sendAsync(message);
    }
}
