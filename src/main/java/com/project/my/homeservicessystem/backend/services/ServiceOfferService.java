package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.ServiceOffer;
import com.project.my.homeservicessystem.backend.exceptions.ServiceOfferException;
import com.project.my.homeservicessystem.backend.repositories.ServiceOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceOfferService {
    private final ServiceOfferRepository repository;

    public ServiceOffer addServiceOffer(ServiceOffer serviceOffer) {
        if (serviceOffer.getStartHour() < 0 || serviceOffer.getStartHour() > 24)
            throw new ServiceOfferException("Start hour is NOT valid.");
        if (serviceOffer.getDurationInHours() < 0)
            throw new ServiceOfferException("Duration is NOT valid.");
        if (serviceOffer.getPrice() < serviceOffer.getRequest().getService().getBasePrice())
            throw new ServiceOfferException("Price is NOT valid.");
        try {
            return repository.save(serviceOffer);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException)
                throw new ServiceOfferException("Offer is Duplicate.");
            throw new ServiceOfferException("Something wrong while adding new Offer", e);
        }
    }

   /* public List<ServiceRequest> getAllServiceRequests() {
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
    }*/
    }

