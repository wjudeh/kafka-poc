package com.stc.kafkapoc.service;

import com.stc.kafkapoc.repository.Notification;
import com.stc.kafkapoc.repository.NotificationCenterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ConsumerListener {

    private final NotificationCenterRepository notificationCenterRepository;

    @KafkaListener(topics = "demo-java", groupId = "group-1")
    public String polling(List<String> list) {

        for (String message : list) {
            log.info("message is => {}", message);
        }

        return null;
    }

    @KafkaListener(topics = "first-topic", groupId = "group-2")
    public String saveToDatabase(List<String> list) {

        for (String message : list) {

            Notification notification = new Notification();
            notification.setValue(message);
            notificationCenterRepository.save(notification);
            log.info("saved! => {}", message);
        }

        return null;
    }
}
