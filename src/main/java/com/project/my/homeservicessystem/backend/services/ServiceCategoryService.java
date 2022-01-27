package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import com.project.my.homeservicessystem.backend.exceptions.ServiceCategoryException;
import com.project.my.homeservicessystem.backend.repositories.ServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceCategoryService {
    private final ServiceCategoryRepository repository;

    public ServiceCategory addServiceCategory(ServiceCategory serviceCategory) throws ServiceCategoryException {
        try {
            return repository.save(serviceCategory);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException)
                throw new ServiceCategoryException("Duplicate Name");
            throw new ServiceCategoryException("Some thing wrong while adding new service category", e);
        }
    }

    public List<ServiceCategory> getAllServiceCategories() {
        return repository.findAll();
    }

    public ServiceCategory getServiceCategoryByName(String name) {
        return repository.findByName(name);
    }

    public ServiceCategory getServiceCategoryById(Long id) throws ServiceCategoryException {
        return repository.findById(id).orElseThrow(() -> new ServiceCategoryException("Category ID is not exists"));
    }

    public boolean updateServiceCategory(ServiceCategory serviceCategory) {
        if (serviceCategory.getId() == null || repository.findById(serviceCategory.getId()).isPresent() == false)
            return false;
        repository.save(serviceCategory);
        return true;
    }

    public boolean deleteServiceCategoryById(Long id) {
        if (id == null || repository.findById(id).isPresent() == false)
            return false;
        repository.deleteById(id);
        return true;
    }
}
