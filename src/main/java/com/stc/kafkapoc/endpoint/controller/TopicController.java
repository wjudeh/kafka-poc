package com.stc.kafkapoc.endpoint.controller;

import com.stc.kafkapoc.endpoint.dto.TopicDTO;
import com.stc.kafkapoc.endpoint.wrapper.TopicWrapper;
import com.stc.kafkapoc.service.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Uuid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

 import java.util.List;

import static com.stc.kafkapoc.endpoint.mapper.TopicMapper.MAPPER;

@AllArgsConstructor
@RestController
public class TopicController {

    private final TopicService topicService;

    @PostMapping("/create")
    public ResponseEntity<TopicWrapper> create(@Valid @RequestBody TopicDTO topicDto) {

        NewTopic topic = MAPPER.map(topicDto);

        CreateTopicsResult createTopicsResult = topicService.create(topic);

        KafkaFuture<Uuid> uuidKafkaFuture = createTopicsResult.topicId(topicDto.getName());

        TopicWrapper topicWrapper = MAPPER.map(uuidKafkaFuture.isDone());

        return ResponseEntity.ok(topicWrapper);
    }

    @GetMapping("/topics")
    public ResponseEntity<List<String>> list() {

        List<String> topics = topicService.getAll();

        return ResponseEntity.ok(topics);
    }
}
