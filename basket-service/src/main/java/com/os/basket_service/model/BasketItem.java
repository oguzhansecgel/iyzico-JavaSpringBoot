package com.os.basket_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketItem implements Serializable {
    private ProductDto product;
    private int quantity;
    private BigDecimal totalPrice;

}
