package com.os.customer_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gsmNumber;
    private String identityNumber;
    private String address;
    private String city;
    private String country;
    private String zipCode;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customer;
}
