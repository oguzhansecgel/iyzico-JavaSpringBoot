package com.os.product_service.controller;

import com.os.product_service.model.PaymentRequest;
import com.os.product_service.service.PaymentService;
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

    // Ödeme işlemi başlat
    @PostMapping("/{productId}/{customerId}")
    public ResponseEntity<String> makePayment(
            @PathVariable Long productId,
            @PathVariable Long customerId,
            @RequestBody PaymentRequest paymentRequest) {

        String result = paymentService.makePayment(productId, customerId,paymentRequest);

        if (result.contains("successful")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if (result.contains("Product not found")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
