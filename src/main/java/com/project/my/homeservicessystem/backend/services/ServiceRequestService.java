package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.ServiceRequest;
import com.project.my.homeservicessystem.backend.exceptions.ServiceRequestException;
import com.project.my.homeservicessystem.backend.repositories.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {
    private final ServiceRequestRepository repository;

    public ServiceRequest addServiceRequest(ServiceRequest request) {
        if (request.getStartDate().before(new Date()))
            throw new ServiceRequestException("Start date is NOT valid.");
        if (request.getPrice() < request.getService().getBasePrice())
            throw new ServiceRequestException("Price is NOT valid.");
        return repository.save(request);
    }
}
