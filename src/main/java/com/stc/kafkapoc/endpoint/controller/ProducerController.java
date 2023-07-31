package com.stc.kafkapoc.endpoint.controller;

import com.stc.kafkapoc.endpoint.dto.ProducerDTO;
import com.stc.kafkapoc.repository.Notification;
import com.stc.kafkapoc.service.ProducerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.stc.kafkapoc.endpoint.mapper.ProducerMapper.MAPPER;

@AllArgsConstructor
@RestController
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping("/send")
    public void sendMessage(@Valid @RequestBody ProducerDTO producerDTO) {

        ProducerRecord<String, String> producerRecord = MAPPER.map(producerDTO);

        producerService.sendAsync(producerRecord);
    }

    @PostMapping("/moveToKafka")
    public ResponseEntity<List<Notification>> sendMessage() {

        List<Notification> notifications = producerService.sendToKafka();

        return ResponseEntity.ok(notifications);
    }
}
