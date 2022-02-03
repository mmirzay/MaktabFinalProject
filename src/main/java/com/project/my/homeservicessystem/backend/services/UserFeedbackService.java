package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Provider;
import com.project.my.homeservicessystem.backend.entities.users.UserFeedback;
import com.project.my.homeservicessystem.backend.exceptions.UserFeedBackException;
import com.project.my.homeservicessystem.backend.repositories.UserFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFeedbackService {
    private final UserFeedbackRepository repository;

    public UserFeedback addUserFeedback(UserFeedback userFeedback) throws UserFeedBackException {
        if (userFeedback.getRate() < 0 || userFeedback.getRate() > 5)
            throw new UserFeedBackException("Rate is NOT valid");
        if (userFeedback.getProvider().getServices().contains(userFeedback.getService()) == false)
            throw new UserFeedBackException("Service is NOT defined for Provider");
        try {
            return repository.save(userFeedback);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException)
                throw new UserFeedBackException("Feedback is Duplicate.");
            throw new UserFeedBackException("Something happen while adding new feedback.", e);
        }
    }

    public List<UserFeedback> getAllFeedbacks() {
        return repository.findAll();
    }

    public List<UserFeedback> getAllFeedbacksOfCustomer(Customer customer) {
        return repository.findAllByCustomer(customer);
    }

    public List<UserFeedback> getAllFeedbacksOfProvider(Provider provider) {
        return repository.findAllByProvider(provider);
    }

    public List<UserFeedback> getAllFeedbackOfCustomerAboutProvider(Customer customer, Provider provider) {
        return repository.findAllByCustomerAndProvider(customer, provider);
    }

    public boolean deleteUserFeedbackById(Long id) {
        if (id == null || repository.findById(id).isPresent() == false)
            return false;
        repository.deleteById(id);
        return true;
    }

}
