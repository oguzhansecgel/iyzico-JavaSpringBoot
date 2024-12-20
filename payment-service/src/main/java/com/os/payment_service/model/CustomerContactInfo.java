package com.os.payment_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerContactInfo {
    private Long id;
    private String gsmNumber;
    private String identityNumber;
    private String address;
    private String city;
    private String country;
    private String zipCode;
}
