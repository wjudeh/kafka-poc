package com.stc.kafkapoc.service;

import com.stc.kafkapoc.endpoint.dto.ConsumerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.time.Duration.ofMillis;

@Service
@AllArgsConstructor
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConsumer<String, String> kafkaConsumer;

    private final ConsumerFactory<String, String> consumerFactory;

    @Override
    public ConsumerRecords<String, String> polling(ConsumerDTO consumerDTO) {

        kafkaConsumer.subscribe(List.of(consumerDTO.getTopicName()));

        ConsumerRecords<String, String> records = kafkaConsumer.poll(ofMillis(1000));

        for (ConsumerRecord<String, String> consumerRecord : records) {

            log.info("Key: " + consumerRecord.key() + ", Value: " + consumerRecord.value());
            log.info("Partition: " + consumerRecord.partition() + ", Offset: " + consumerRecord.offset());
        }

        return records;
    }


    @Deprecated(since="0.0", forRemoval=true)
    private ConsumerRecords<String, String> receive(ConsumerDTO consumerDTO) {

        TopicPartitionOffset topicPartitionOffset = new TopicPartitionOffset(consumerDTO.getTopicName(), 1, 20L);

        Collection<TopicPartitionOffset> list = List.of(topicPartitionOffset);

        kafkaTemplate.setConsumerFactory(consumerFactory);

        ConsumerRecords<String, String> records = kafkaTemplate.receive(list, ofMillis(1000));

        for (ConsumerRecord<String, String> consumerRecord : records) {

            log.info("Key: " + consumerRecord.key() + ", Value: " + consumerRecord.value());
            log.info("Partition: " + consumerRecord.partition() + ", Offset: " + consumerRecord.offset());
        }

        return records;
    }
}



