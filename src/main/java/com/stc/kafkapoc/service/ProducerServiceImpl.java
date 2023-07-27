package com.stc.kafkapoc.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@AllArgsConstructor
@Slf4j
public class ProducerServiceImpl implements ProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_NAME = "demo-java";

    @Override
    public void sendAsync(String message) {

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, "Key", message);

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(producerRecord);

        future.whenComplete((result, throwable) -> {

            if (throwable == null) {

                handleSuccess(result);

            } else {
                handleFailure(throwable);
            }
        });
    }

    @Override
    public void sendAsync(ProducerRecord<String, String> producerRecord) {

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(producerRecord);

        future.whenComplete((result, throwable) -> {

            if (throwable == null) {

                handleSuccess(result);

            } else {
                handleFailure(throwable);
            }
        });
    }

    @Override
    public void sendSync(String message) {

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, "Key", message);

        try {
            SendResult<String, String> result = kafkaTemplate.send(producerRecord).get(10, SECONDS);
            handleSuccess(result);

        } catch (ExecutionException | InterruptedException e) {
            handleFailure(e);
            Thread.currentThread().interrupt();

        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleFailure(Throwable throwable) {
        log.warn("Message could not be delivered. " + throwable.getMessage());

    }

    private static void handleSuccess(SendResult<String, String> result) {
        log.info("Your message was delivered with following offset: " + result.getRecordMetadata().offset());
    }
}
