package com.os.product_service.dto.response.category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdCategoryResponse implements Serializable {
    private Long id;
    private String name;
}
