package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.ServiceOffer;
import com.project.my.homeservicessystem.backend.entities.services.ServiceRequest;
import com.project.my.homeservicessystem.backend.entities.users.Provider;
import com.project.my.homeservicessystem.backend.exceptions.ServiceOfferException;
import com.project.my.homeservicessystem.backend.repositories.ServiceOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceOfferService {
    private final ServiceOfferRepository repository;

    private static final Sort BY_PRICE_ASC = Sort.by(Sort.Order.asc("price"));

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

    public List<ServiceOffer> getAllServiceOffers() {
        return repository.findAll();
    }

    public List<ServiceOffer> getOffersOfProvider(Provider provider) {
        return repository.findAllByProvider(provider);
    }

    public List<ServiceOffer> getOffersOfServiceRequest(ServiceRequest request) {
        return repository.findAllByRequest(request);
    }

    public List<ServiceOffer> getAllServiceOffersOrderByPrice() {
        return repository.findAll(BY_PRICE_ASC);
    }

    public boolean updateServiceOffer(ServiceOffer serviceOffer) {
        if (serviceOffer.getId() == null || repository.findById(serviceOffer.getId()).isPresent() == false)
            return false;
        repository.save(serviceOffer);
        return true;
    }

    public boolean deleteServiceOfferById(Long id) {
        if (id == null || repository.findById(id).isPresent() == false)
            return false;
        repository.deleteById(id);
        return true;
    }

}

