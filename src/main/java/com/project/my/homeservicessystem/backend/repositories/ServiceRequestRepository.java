package com.project.my.homeservicessystem.backend.repositories;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.services.ServiceRequest;
import com.project.my.homeservicessystem.backend.entities.services.ServiceRequestStatus;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findAllByService(Service service);

    List<ServiceRequest> findAllByCustomer(Customer customer);

    List<ServiceRequest> findByStatus(ServiceRequestStatus status);
    List<ServiceRequest> findByStartDateGreaterThanEqual(Date date);
}
