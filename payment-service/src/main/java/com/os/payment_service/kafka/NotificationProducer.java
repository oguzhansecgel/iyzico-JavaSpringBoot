package com.os.payment_service.kafka;

import com.os.payment_service.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, Notification> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    public void sendMessage(Notification message) {
        log.info("Sending message: " + message);
        kafkaTemplate.send(defaultTopic, message);

    }
}
