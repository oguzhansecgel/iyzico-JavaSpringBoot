package com.os.customer_service.dto.response.contactinfo;

import com.os.customer_service.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetByContactInfoWithCustomerIdResponse {
    private Long id;
    private String gsmNumber;
    private String identityNumber;
    private String address;
    private String city;
    private String country;
    private String zipCode;
    private Long customerId;
}
