package com.os.customer_service.repository;

import com.os.customer_service.model.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
    List<ContactInfo> findByCustomerId(Long contactId);
}
