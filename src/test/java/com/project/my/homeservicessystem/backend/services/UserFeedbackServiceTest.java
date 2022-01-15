package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Provider;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.entities.users.UserFeedback;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserFeedbackServiceTest {

    @Autowired
    UserFeedbackService feedbackService;

    @Autowired
    RoleService roleService;

    @Autowired
    ProviderService providerService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ServiceCategoryService categoryService;

    @Autowired
    ServiceService serviceManager;

    private Customer customer1;
    private Customer customer2;

    private Provider provider1;
    private Provider provider2;

    private Service service1;
    private Service service2;

    @BeforeAll
    void setUp() {
        ServiceCategory category1 = new ServiceCategory("House Repairs");
        ServiceCategory category2 = new ServiceCategory("House Cleaning");
        categoryService.addServiceCategory(category1);
        categoryService.addServiceCategory(category2);

        service1 = Service.of("Service1", 1000, category1);
        service2 = Service.of("Service2", 2000, category2);
        serviceManager.addService(service1);
        serviceManager.addService(service2);

        Role role1 = new Role("Provider");
        Role role2 = new Role("Customer");
        roleService.addRole(role1);
        roleService.addRole(role2);

        provider1 = Provider.of("provider1@provider.com", "Abcd1234", role1);
        provider2 = Provider.of("provider2@provider.com", "Abcd1234", role1);
        provider1.getServices().add(service1);
        provider1.getServices().add(service2);
        provider2.getServices().add(service1);
        provider2.getServices().add(service2);
        providerService.addProvider(provider1);
        providerService.addProvider(provider2);

        customer1 = Customer.of("mirzay.mohsen@gmail.com", "Abcd1234", role2);
        customer2 = Customer.of("mmirzay@ut.ac.ir", "Abcd1234", role2);
        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);

    }

    @Test
    @Order(1)
    void addUserFeedback() {
        UserFeedback feedback1 = UserFeedback.of(customer1, provider1, service1, 5);
        UserFeedback addedFeedback1 = feedbackService.addUserFeedback(feedback1);
        assertNotNull(addedFeedback1);

        UserFeedback feedback2 = UserFeedback.of(customer2, provider2, service2, 5);
        UserFeedback addedFeedback2 = feedbackService.addUserFeedback(feedback2);
        assertNotNull(addedFeedback2);

        UserFeedback duplicateFeedback = UserFeedback.of(customer1, provider1, service1, 5);
        assertThrows(Exception.class, () -> feedbackService.addUserFeedback(duplicateFeedback));

        UserFeedback invalidRate = UserFeedback.of(customer1, provider1, service2, -1);
        assertThrows(Exception.class, () -> feedbackService.addUserFeedback(invalidRate));

        UserFeedback feedback3 = UserFeedback.of(customer1, provider1, service2, 5);
        assertNotNull(feedbackService.addUserFeedback(feedback3));

        UserFeedback feedback4 = UserFeedback.of(customer2, provider1, service1, 3);
        assertNotNull(feedbackService.addUserFeedback(feedback4));

        UserFeedback feedback5 = UserFeedback.of(customer1, provider2, service1, 1);
        assertNotNull(feedbackService.addUserFeedback(feedback5));
    }

    @Test
    @Order(2)
    void getAllFeedbacks() {
        List<UserFeedback> allFeedbacks = feedbackService.getAllFeedbacks();
        assertEquals(5, allFeedbacks.size());
    }

    @Test
    @Order(3)
    void getAllFeedbacksOfCustomer() {
        List<UserFeedback> allFeedbacksOfCustomer1 = feedbackService.getAllFeedbacksOfCustomer(customer1);
        assertEquals(3, allFeedbacksOfCustomer1.size());

        List<UserFeedback> allFeedbacksOfCustomer2 = feedbackService.getAllFeedbacksOfCustomer(customer2);
        assertEquals(2, allFeedbacksOfCustomer2.size());
    }

    @Test
    @Order(4)
    void getAllFeedbacksOfProvider() {
        List<UserFeedback> allFeedbacksOfProvider1 = feedbackService.getAllFeedbacksOfProvider(provider1);
        assertEquals(3, allFeedbacksOfProvider1.size());

        List<UserFeedback> allFeedbacksOfProvider2 = feedbackService.getAllFeedbacksOfProvider(provider2);
        assertEquals(2, allFeedbacksOfProvider2.size());
    }

    @Test
    @Order(5)
    void getAllFeedbackOfCustomerAboutProvider() {
        List<UserFeedback> allFeedbackOfCustomerAboutProvider = feedbackService.getAllFeedbackOfCustomerAboutProvider(customer1, provider1);
        assertEquals(2, allFeedbackOfCustomerAboutProvider.size());
    }

    @Test
    @Order(6)
    void deleteUserFeedbackById() {
        UserFeedback toRemove = feedbackService.getAllFeedbackOfCustomerAboutProvider(customer1, provider2).get(0);
        assertNotNull(toRemove);

        assertTrue(feedbackService.deleteUserFeedbackById(toRemove.getId()));
        assertFalse(feedbackService.deleteUserFeedbackById(toRemove.getId()));

        assertEquals(0, feedbackService.getAllFeedbackOfCustomerAboutProvider(customer1, provider2).size());
    }
}