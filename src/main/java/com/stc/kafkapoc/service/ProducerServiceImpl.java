package com.stc.kafkapoc.service;

import com.stc.kafkapoc.exception.NcWebServiceException;
import com.stc.kafkapoc.repository.Notification;
import com.stc.kafkapoc.repository.NotificationCenterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.stc.kafkapoc.exception.dto.ErrorCode.CONNECTIONS_TIMEOUT;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.springframework.http.HttpStatus.REQUEST_TIMEOUT;

@Service
@AllArgsConstructor
@Slf4j
public class ProducerServiceImpl implements ProducerService {

    private static final String DEFAULT_KEY_VALUE = "Key";
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final NotificationCenterRepository notificationCenterRepository;

    @Override
    public void sendAsync(String message) {

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(DEFAULT_TOPIC_NAME, DEFAULT_KEY_VALUE, message);

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

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(DEFAULT_TOPIC_NAME, DEFAULT_KEY_VALUE, message);

        try {
            SendResult<String, String> result = kafkaTemplate.send(producerRecord).get(10, SECONDS);
            handleSuccess(result);

        } catch (ExecutionException | InterruptedException e) {

            handleFailure(e);
            Thread.currentThread().interrupt();

        } catch (TimeoutException e) {

            throw NcWebServiceException.build(REQUEST_TIMEOUT, e)
                    .addMessage(CONNECTIONS_TIMEOUT, e.getMessage());
        }
    }

    @Override
    public List<Notification> sendToKafka() {

        List<Notification> notifications = notificationCenterRepository.findAll();

        for (Notification notification : notifications) {
            sendSync(notification.getValue());
        }

        return notifications;
    }

    private static void handleFailure(Throwable throwable) {
        log.warn("Message could not be delivered. " + throwable.getMessage());

    }

    private static void handleSuccess(SendResult<String, String> result) {
        log.info("Your message was delivered with following offset: " + result.getRecordMetadata().offset());
    }
}
