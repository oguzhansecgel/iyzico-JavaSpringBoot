package com.os.notification_service.kafka;

import com.os.notification_service.model.OrderNotification;
import com.os.notification_service.service.OrderNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final OrderNotificationService orderNotificationService;
    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void createConsumer(ConsumerRecord<String, OrderNotification> payload) {
        orderNotificationService.sendOrderCompletedEmail(payload.value());
        System.out.println("Consumer tarafından mesaj alındı  : " + payload.value());
    }
}
