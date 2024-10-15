package com.os.customer_service.service.impl;

import com.os.customer_service.dto.request.contactinfo.CreateContactInfoRequest;
import com.os.customer_service.dto.request.contactinfo.UpdateContactInfoRequest;
import com.os.customer_service.dto.response.contactinfo.*;
import com.os.customer_service.exception.exceptionMessage.ContactInfoMessage;
import com.os.customer_service.exception.type.ContactInfoNotFoundException;
import com.os.customer_service.mapper.ContactInfoMapper;
import com.os.customer_service.model.ContactInfo;
import com.os.customer_service.model.User;
import com.os.customer_service.repository.ContactInfoRepository;
import com.os.customer_service.repository.UserRepository;
import com.os.customer_service.service.ContactInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

    private final ContactInfoRepository contactInfoRepository;
    private final UserRepository userRepository;

    public ContactInfoServiceImpl(ContactInfoRepository contactInfoRepository, UserRepository userRepository) {
        this.contactInfoRepository = contactInfoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CreateContactInfoResponse createContactInfo(CreateContactInfoRequest request) {
        ContactInfo contactInfo = ContactInfoMapper.INSTANCE.createContactInfo(request);
        Optional<User> customerOptional = userRepository.findById(request.getCustomerId());
        contactInfo.setCustomer(customerOptional.get());
        ContactInfo savedContactInfo = contactInfoRepository.save(contactInfo);

        return new CreateContactInfoResponse(savedContactInfo.getId(),savedContactInfo.getGsmNumber(),savedContactInfo.getIdentityNumber(),savedContactInfo.getAddress(),savedContactInfo.getCity(),savedContactInfo.getCountry(), savedContactInfo.getZipCode(), savedContactInfo.getCustomer().getId());
    }

    @Override
    public UpdateContactInfoResponse updateContactInfo(Long contactInfoId,UpdateContactInfoRequest request) {
        Optional<ContactInfo> optionalContactInfo = contactInfoRepository.findById(contactInfoId);
        if (optionalContactInfo.isEmpty())
        {
            throw new ContactInfoNotFoundException(ContactInfoMessage.CONTACT_INFO_NOT_FOUND);
        }
        ContactInfo contactInfoExisting = optionalContactInfo.get();
        ContactInfo contactInfo = ContactInfoMapper.INSTANCE.updateContactInfo(request,contactInfoExisting);
        Optional<User> customerOptional = userRepository.findById(request.getCustomerId());
        contactInfo.setCustomer(customerOptional.get());
        ContactInfo savedContactInfo = contactInfoRepository.save(contactInfo);

        return new UpdateContactInfoResponse(savedContactInfo.getId(),savedContactInfo.getGsmNumber(),savedContactInfo.getIdentityNumber(),savedContactInfo.getAddress(),savedContactInfo.getCity(),savedContactInfo.getCountry(),savedContactInfo.getZipCode(),savedContactInfo.getCustomer().getId());
    }

    @Override
    public List<GetAllContactInfoResponse> getAllContactInfo() {
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        return ContactInfoMapper.INSTANCE.contactInfoToContactInfoList(contactInfoList);
    }

    @Override
    public Optional<GetByIdContactInfoResponse> getByIdContactInfo(Long id) {
        Optional<ContactInfo> optionalContactInfo = contactInfoRepository.findById(id);
        if (optionalContactInfo.isEmpty())
        {
            throw new ContactInfoNotFoundException(ContactInfoMessage.CONTACT_INFO_NOT_FOUND);
        }
        return optionalContactInfo.map(ContactInfoMapper.INSTANCE::getByIdContactInfoResponse);
    }

    @Override
    public void deleteContactInfo(Long id) {
        Optional<ContactInfo> optionalContactInfo = contactInfoRepository.findById(id);
        if (optionalContactInfo.isEmpty())
        {
            throw new ContactInfoNotFoundException(ContactInfoMessage.CONTACT_INFO_NOT_FOUND);
        }
        contactInfoRepository.deleteById(id);
    }

    @Override
    public List<GetByContactInfoWithCustomerIdResponse> getByCustomerId(Long id) {
        List<ContactInfo> contactInfoList = contactInfoRepository.findByCustomerId(id);

        return ContactInfoMapper.INSTANCE.contactInfoListToContactInfoWithCustomerIdResponseList(contactInfoList);

    }
}
