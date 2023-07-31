package com.stc.kafkapoc.service;

import com.stc.kafkapoc.endpoint.dto.ConsumerDTO;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public interface ConsumerService {

    ConsumerRecords<String, String> polling(ConsumerDTO consumerDTO);
}
