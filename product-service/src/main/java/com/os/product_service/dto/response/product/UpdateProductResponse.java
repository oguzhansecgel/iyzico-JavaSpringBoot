package com.os.product_service.dto.response.product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
}
