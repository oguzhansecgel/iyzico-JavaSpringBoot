package com.os.payment_service.service;

import com.os.payment_service.model.PaymentRequest;

public interface PaymentService {
    String makePayment(String orderId, Long customerId,PaymentRequest paymentRequest);
    void order(String orderId);
}
