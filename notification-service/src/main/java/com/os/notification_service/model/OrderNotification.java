package com.os.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotification {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private BigDecimal totalPrice;
    private int quantity;
    private String name;
    private String description;
    private BigDecimal price;
}
