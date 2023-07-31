package com.stc.kafkapoc.service;

import com.stc.kafkapoc.repository.Notification;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;

public interface ProducerService {

    String DEFAULT_TOPIC_NAME = "default-topic";

    void sendAsync(String message);

    void sendAsync(ProducerRecord<String, String> producerRecord);

    void sendSync(String message);

    List<Notification> sendToKafka();
}
