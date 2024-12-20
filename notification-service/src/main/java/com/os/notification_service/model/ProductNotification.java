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
public class ProductNotification {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
}