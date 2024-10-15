package com.os.customer_service.dto.request.contactinfo;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateContactInfoRequest {
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^[0-9]{11}$", message = "GSM number must be numeric and exactly 11 digits long.")
    private String gsmNumber;
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^[0-9]{11}$", message = "GSM number must be numeric and exactly 11 digits long.")
    private String identityNumber;
    private String address;
    private String city;
    private String country;
    private String zipCode;
    private Long customerId;
}
