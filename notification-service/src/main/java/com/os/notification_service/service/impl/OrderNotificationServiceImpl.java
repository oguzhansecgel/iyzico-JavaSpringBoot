package com.os.notification_service.service.impl;

import com.os.notification_service.model.OrderNotification;
import com.os.notification_service.service.OrderNotificationService;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import javax.management.Notification;

@Service
public class OrderNotificationServiceImpl implements OrderNotificationService {


    private final MailSender mailSender;

    public OrderNotificationServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOrderCompletedEmail(OrderNotification notification) {
        System.out.println("kafka çalışıyor");
    }
}
