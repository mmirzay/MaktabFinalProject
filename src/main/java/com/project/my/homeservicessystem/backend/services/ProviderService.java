package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.users.Provider;
import com.project.my.homeservicessystem.backend.exceptions.ProviderException;
import com.project.my.homeservicessystem.backend.repositories.ProviderRepository;
import com.project.my.homeservicessystem.backend.utilities.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderService {
    private final ProviderRepository repository;
    public Provider addProvider(Provider provider) {
        if (Validator.validateEmail(provider.getEmail()) == false)
            throw new ProviderException("Email is NOT valid");

        if (Validator.validatePassword(provider.getPassword()) == false)
            throw new ProviderException("Password is NOT valid. Must include at least 8 character mixed with lower-upper and numbers");

        try {
            return repository.save(provider);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException)
                throw new ProviderException("Duplicate Email");
            throw new ProviderException("Some thing wrong while adding new provider", e);
        }
    }

    public List<Provider> getAllProviders() {
        return repository.findAll();
    }

    public Provider getProviderByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean updateProvider(Provider provider) {
        if (provider.getId() == null || repository.findById(provider.getId()).isPresent() == false)
            return false;
        repository.save(provider);
        return true;
    }

    public boolean deleteProviderById(Long id) {
        if (id == null || repository.findById(id).isPresent() == false)
            return false;
        repository.deleteById(id);
        return true;
    }
}
