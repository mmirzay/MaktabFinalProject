package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.services.ServiceRequest;
import com.project.my.homeservicessystem.backend.entities.services.ServiceRequestStatus;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.exceptions.ServiceRequestException;
import com.project.my.homeservicessystem.backend.repositories.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceRequestService {
    private final ServiceRequestRepository repository;

    public ServiceRequest addServiceRequest(ServiceRequest request) throws ServiceRequestException {
        //FIXME is it true to check current date?
        if (request.getStartDate().before(new Date()))
            throw new ServiceRequestException("Start date is NOT valid.");
        if (request.getPrice() < request.getService().getBasePrice())
            throw new ServiceRequestException("Price is NOT valid.");
        try {
            return repository.save(request);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException)
                throw new ServiceRequestException("Request is duplicate");
            throw new ServiceRequestException("Something wrong while adding new request", e);
        }
    }

    public List<ServiceRequest> getAllServiceRequests() {
        return repository.findAll();
    }

    public List<ServiceRequest> getRequestsOfService(Service service) {
        return repository.findAllByService(service);
    }

    public List<ServiceRequest> getRequestsOfCustomer(Customer customer) {
        return repository.findAllByCustomer(customer);
    }

    public boolean updateServiceRequest(ServiceRequest serviceRequest) {
        if (serviceRequest.getId() == null || repository.findById(serviceRequest.getId()).isPresent() == false)
            return false;
        repository.save(serviceRequest);
        return true;
    }

    public List<ServiceRequest> getRequestsByStatus(ServiceRequestStatus status) {
        return repository.findByStatus(status);
    }

    public List<ServiceRequest> getRequestsWithStartDateAfter(Date date) {
        return repository.findByStartDateGreaterThanEqual(date);
    }

    public boolean deleteServiceRequestById(Long id) {
        if (id == null || repository.findById(id).isPresent() == false)
            return false;
        repository.deleteById(id);
        return true;
    }
}
