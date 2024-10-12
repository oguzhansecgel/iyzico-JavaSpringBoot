package com.os.customer_service.controller;

import com.os.customer_service.dto.request.contactinfo.CreateContactInfoRequest;
import com.os.customer_service.dto.request.contactinfo.UpdateContactInfoRequest;
import com.os.customer_service.dto.response.contactinfo.*;
import com.os.customer_service.service.ContactInfoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/contactInfo")
public class ContactInfoController {

    private final ContactInfoService contactInfoService;

    public ContactInfoController(ContactInfoService contactInfoService) {
        this.contactInfoService = contactInfoService;
    }

    @GetMapping("/list/contactInfo")
    public List<GetAllContactInfoResponse> getAllContactInfoResponse()
    {
        return contactInfoService.getAllContactInfo();
    }

    @GetMapping("/getById/contactInfo/{contactInfoId}")
    public Optional<GetByIdContactInfoResponse> getByIdContactInfoResponse(@PathVariable Long contactInfoId)
    {
        return contactInfoService.getByIdContactInfo(contactInfoId);
    }
    @GetMapping("/customer/{customerId}")
    public List<GetByContactInfoWithCustomerIdResponse> getContactInfosByCustomerId(@PathVariable Long customerId) {
        return contactInfoService.getByCustomerId(customerId);
    }
    @DeleteMapping("/delete/contactInfo/{contactInfoId}")
    public void deleteContactInfo(@PathVariable Long contactInfoId)
    {
        contactInfoService.deleteContactInfo(contactInfoId);
    }
    @PostMapping("/create/contactInfo")
    public CreateContactInfoResponse createContactInfo(@Valid @RequestBody CreateContactInfoRequest createContactInfoRequest)
    {
        return contactInfoService.createContactInfo(createContactInfoRequest);
    }
    @PutMapping("/update/contactInfo/{contactInfoId}")
    public UpdateContactInfoResponse updateContactInfo(@PathVariable Long contactInfoId,@RequestBody UpdateContactInfoRequest updateContactInfoRequest)
    {
        return contactInfoService.updateContactInfo(contactInfoId, updateContactInfoRequest);
    }
}
