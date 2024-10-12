package com.os.customer_service.mapper;

import com.os.customer_service.dto.request.contactinfo.CreateContactInfoRequest;
import com.os.customer_service.dto.request.contactinfo.UpdateContactInfoRequest;
import com.os.customer_service.dto.response.contactinfo.GetAllContactInfoResponse;
import com.os.customer_service.dto.response.contactinfo.GetByContactInfoWithCustomerIdResponse;
import com.os.customer_service.dto.response.contactinfo.GetByIdContactInfoResponse;
import com.os.customer_service.model.ContactInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContactInfoMapper {

        ContactInfoMapper INSTANCE = Mappers.getMapper(ContactInfoMapper.class);

        ContactInfo createContactInfo(CreateContactInfoRequest createContactInfoRequest);

        ContactInfo updateContactInfo(UpdateContactInfoRequest updateContactInfoRequest, @MappingTarget ContactInfo contactInfo);

        GetByIdContactInfoResponse getByIdContactInfoResponse(ContactInfo contactInfo);

        @Mapping(source = "customer.id",target = "customerId")
        GetAllContactInfoResponse getAllContactInfo(ContactInfo contactInfo);
        List<GetAllContactInfoResponse> contactInfoToContactInfoList(List<ContactInfo> contactInfoList);

        @Mapping(source = "customer.id", target = "customerId")
        GetByContactInfoWithCustomerIdResponse contactInfoToContactInfoWithCustomerIdResponse(ContactInfo contactInfo);

        List<GetByContactInfoWithCustomerIdResponse> contactInfoListToContactInfoWithCustomerIdResponseList(List<ContactInfo> contactInfoList);
}
