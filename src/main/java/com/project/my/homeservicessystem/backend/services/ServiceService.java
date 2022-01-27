package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import com.project.my.homeservicessystem.backend.exceptions.ServiceException;
import com.project.my.homeservicessystem.backend.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository repository;

    public Service addService(Service service) throws ServiceException {
        if (service.getBasePrice() < 0)
            throw new ServiceException("Invalid base price.");
        try {
            return repository.save(service);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException)
                throw new ServiceException("Duplicate Service title");
            throw new ServiceException("Some thing wrong while adding new Service", e);
        }
    }

    public List<Service> getAllServices() {
        return repository.findAll();
    }

    public List<Service> getAllServicesByCategory(ServiceCategory category) {
        return repository.findAllByCategory(category);
    }

    public Service getServiceByTitle(String title) {
        return repository.findByTitle(title);
    }

    public Service getServiceById(Long id) throws ServiceException {
        return repository.findById(id).orElseThrow(() -> new ServiceException("Service Id is not exists."));
    }

    public boolean updateService(Service service) {
        if (service.getId() == null || repository.findById(service.getId()).isPresent() == false)
            return false;
        repository.save(service);
        return true;
    }

    public boolean deleteServiceById(Long id) {
        if (id == null || repository.findById(id).isPresent() == false)
            return false;
        repository.deleteById(id);
        return true;
    }
}
