package com.stc.kafkapoc.service;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface ProducerService {
    void sendAsync(String message);

    void sendAsync(ProducerRecord<String, String> producerRecord);

    void sendSync(String message);
}
