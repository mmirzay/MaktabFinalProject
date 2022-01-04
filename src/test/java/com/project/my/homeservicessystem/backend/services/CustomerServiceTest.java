package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    CustomerService service;

    @Autowired
    RoleService roleService;

    private Role role1;
    private Role role2;

    @BeforeEach
    void setUp() {
        role1 = new Role("Customer");
        role2 = new Role("User");
        roleService.addRole(role1);
        roleService.addRole(role2);
    }

    @Test
    void addCustomer() {
        String validEmail1 = "mirzay.mohsen@gmail.com";
        String validPassword1 = "abcd1234";

        Customer customer1 = Customer.of(validEmail1, validPassword1, role1);
        Customer addedCustomer1 = service.addCustomer(customer1);
        assertNotNull(addedCustomer1);

        Customer duplicateEmail = Customer.of(validEmail1,validPassword1,role1);
        assertThrows(Exception.class,() -> service.addCustomer(duplicateEmail));

        String invalidEmail = "@m";
        Customer customerWithInvalidEmail  = Customer.of(invalidEmail,validPassword1,role1);
        assertThrows(Exception.class,() -> service.addCustomer(customerWithInvalidEmail));
    }
}