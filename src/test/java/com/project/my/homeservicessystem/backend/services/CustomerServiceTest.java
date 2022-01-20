package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.exceptions.CustomerException;
import com.project.my.homeservicessystem.backend.exceptions.RoleException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    @Autowired
    CustomerService service;

    @Autowired
    RoleService roleService;

    private Role role1;
    private Role role2;

    @BeforeAll
    void setUp() throws Exception {
        role1 = new Role("Customer");
        role2 = new Role("User");
        roleService.addRole(role1);
        roleService.addRole(role2);
    }

    @Test
    @Order(1)
    void addCustomer() throws Exception {
        String validEmail1 = "mirzay.mohsen@gmail.com";
        String validPassword1 = "Abcd1234";

        Customer customer1 = Customer.of(validEmail1, validPassword1, role1);
        customer1.getRoles().add(role2);
        Customer addedCustomer1 = service.addCustomer(customer1);
        assertNotNull(addedCustomer1);

        Customer duplicateEmail = Customer.of(validEmail1, validPassword1, role1);
        assertThrows(Exception.class, () -> service.addCustomer(duplicateEmail));

        String invalidEmail = "@m";
        Customer customerWithInvalidEmail = Customer.of(invalidEmail, validPassword1, role1);
        assertThrows(Exception.class, () -> service.addCustomer(customerWithInvalidEmail));

        String validEmail2 = "validEmail2@mail.com";
        String invalidPassword = "1234567";
        Customer customerWithInvalidPassword = Customer.of(validEmail2, invalidPassword, role1);
        assertThrows(Exception.class, () -> service.addCustomer(customerWithInvalidPassword));

        String validPassword2 = "Abcd1234";
        Customer customer2 = Customer.of(validEmail2, validPassword2, role1);

        Customer addedCustomer2 = service.addCustomer(customer2);
        assertNotNull(addedCustomer2);

    }

    @Test
    @Order(2)
    void getAllCustomers() {
        List<Customer> allCustomers = service.getAllCustomers();
        assertEquals(allCustomers.size(), 2);
    }

    @Test
    @Order(3)
    void getCustomerByEmail() {
        Customer found = service.getCustomerByEmail("mirzay.mohsen@gmail.com");
        assertNotNull(found);

        Customer notFound = service.getCustomerByEmail("some@email.com");
        assertNull(notFound);
    }

    @Test
    @Order(4)
    void updateCustomer() {
        String email = "mirzay.mohsen@gmail.com";
        Customer found = service.getCustomerByEmail(email);
        assertNotNull(found);

        found.setFirstName("Mohsen");
        found.setLastName("Mirzaei");
        assertTrue(service.updateCustomer(found));
        Customer updated = service.getCustomerByEmail(email);
        assertEquals(updated.getFirstName(), "Mohsen");
    }

    @Test
    @Order(5)
    void deleteCustomerById() {
        Customer toRemove = service.getCustomerByEmail("validEmail2@mail.com");
        assertNotNull(toRemove);
        assertTrue(service.deleteCustomerById(toRemove.getId()));
        assertEquals(service.getAllCustomers().size(), 1);
    }
}