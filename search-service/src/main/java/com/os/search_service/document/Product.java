package com.os.search_service.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "products")
public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
}
