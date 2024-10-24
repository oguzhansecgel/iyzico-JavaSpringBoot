package com.os.payment_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private BigDecimal totalPrice;
    private List<ProductNotification> products;
}
