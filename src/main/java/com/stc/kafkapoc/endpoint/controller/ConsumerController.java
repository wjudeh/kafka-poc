package com.stc.kafkapoc.endpoint.controller;

import com.stc.kafkapoc.endpoint.dto.ConsumerDTO;
import com.stc.kafkapoc.service.ConsumerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ConsumerController {

    private final ConsumerService consumerService;

    @PostMapping("/polling")
    public ResponseEntity<ConsumerRecords<String, String>> polling(@Valid @RequestBody ConsumerDTO consumerDTO) {

        ConsumerRecords<String, String> consumerRecords = consumerService.polling(consumerDTO);

        return ResponseEntity.ok(consumerRecords);
    }
}
