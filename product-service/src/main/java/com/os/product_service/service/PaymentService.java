package com.os.product_service.service;

import com.os.product_service.model.PaymentRequest;

public interface PaymentService {

    String makePayment(int productId, int customerId, PaymentRequest paymentRequest);
}
