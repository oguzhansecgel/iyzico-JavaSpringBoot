package com.os.notification_service.service;

import com.os.notification_service.model.OrderNotification;

import javax.management.Notification;

public interface OrderNotificationService {

    void sendOrderCompletedEmail(OrderNotification notification);

}
