package com.os.basket_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "baskets")
public class Basket {
    @Id
    private String id;
    private CustomerDto customer;
    private List<BasketItem> items;
    private BigDecimal totalPrice;

    public void updateTotalPrice() {
        totalPrice = items.stream()
                .map(BasketItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}