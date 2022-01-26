package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.exceptions.CustomerException;
import com.project.my.homeservicessystem.backend.repositories.CustomerRepository;
import com.project.my.homeservicessystem.backend.utilities.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public Customer addCustomer(Customer customer) throws CustomerException {
        if (Validator.validateEmail(customer.getEmail()) == false)
            throw new CustomerException("Email is NOT valid");

        if (Validator.validatePassword(customer.getPassword()) == false)
            throw new CustomerException("Password is NOT valid. Must include at least 8 character mixed with lower-upper and numbers");

        try {
            return repository.save(customer);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException)
                throw new CustomerException("Duplicate Email");
            throw new CustomerException("Some thing wrong while adding new customer", e);
        }
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Customer getCustomerById(Long id) throws CustomerException {
        return repository.findById(id).orElseThrow(() -> new CustomerException("Customer ID is not exists."));
    }

    public Customer getCustomerByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean updateCustomer(Customer customer) {
        if (customer.getId() == null || repository.findById(customer.getId()).isPresent() == false)
            return false;
        repository.save(customer);
        return true;
    }

    public boolean deleteCustomerById(Long id) {
        if (id == null || repository.findById(id).isPresent() == false)
            return false;
        repository.deleteById(id);
        return true;
    }


}
