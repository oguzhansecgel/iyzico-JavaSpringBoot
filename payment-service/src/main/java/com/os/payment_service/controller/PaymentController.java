package com.os.payment_service.controller;

import com.os.payment_service.model.Order;
import com.os.payment_service.model.PaymentRequest;
import com.os.payment_service.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @GetMapping("{orderId}")
    public void order(@PathVariable("orderId") String orderId)
    {
        paymentService.order(orderId);
    }
    @PostMapping("/{orderId}/{customerId}")
    public ResponseEntity<String> makePayment(
            @PathVariable String orderId,
            @PathVariable Long customerId,
            @RequestBody PaymentRequest paymentRequest) {
        try {
            // Ödeme işlemini gerçekleştir
            String paymentResult = paymentService.makePayment(orderId,customerId, paymentRequest);
            return new ResponseEntity<>(paymentResult, HttpStatus.OK);
        } catch (Exception e) {
            // Hata durumunda uygun yanıtı döndür
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
