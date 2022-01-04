package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.exceptions.CustomerException;
import com.project.my.homeservicessystem.backend.repositories.CustomerRepository;
import com.project.my.homeservicessystem.backend.utilities.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public Customer addCustomer(Customer customer) {
        if (Validator.validateEmail(customer.getEmail()) == false)
            throw new CustomerException("Email is NOT valid");

        try {
            return repository.save(customer);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException)
                throw new CustomerException("Duplicate Email");
            throw new CustomerException("Some thing wrong while adding new customer", e);
        }
    }
}
