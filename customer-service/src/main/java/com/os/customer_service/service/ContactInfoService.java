package com.os.customer_service.service;

import com.os.customer_service.dto.request.contactinfo.CreateContactInfoRequest;
import com.os.customer_service.dto.request.contactinfo.UpdateContactInfoRequest;
import com.os.customer_service.dto.response.contactinfo.*;

import java.util.List;
import java.util.Optional;

public interface ContactInfoService {

    CreateContactInfoResponse createContactInfo(CreateContactInfoRequest request);
    UpdateContactInfoResponse updateContactInfo(Long contactInfoId, UpdateContactInfoRequest request);
    List<GetAllContactInfoResponse> getAllContactInfo();
    Optional<GetByIdContactInfoResponse> getByIdContactInfo(Long id);
    void deleteContactInfo(Long id);
    List<GetByContactInfoWithCustomerIdResponse> getByCustomerId(Long id);
}
