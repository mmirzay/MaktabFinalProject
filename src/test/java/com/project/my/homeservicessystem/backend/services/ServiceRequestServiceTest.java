package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import com.project.my.homeservicessystem.backend.entities.services.ServiceRequest;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceRequestServiceTest {

    @Autowired
    ServiceRequestService requestService;

    @Autowired
    RoleService roleService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ServiceCategoryService categoryService;

    @Autowired
    ServiceService serviceManager;

    private Customer customer1;
    private Customer customer2;

    private Service service1;
    private Service service2;

    @BeforeAll
    void setUp() {
        Role role = new Role("Customer");
        roleService.addRole(role);

        customer1 = Customer.of("mirzay.mohsen@gmail.com", "Abcd1234", role);
        customer2 = Customer.of("mirzay.mohsen@yahoo.com", "Abcd1234", role);
        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);

        ServiceCategory category1 = new ServiceCategory("House Cleaning");
        ServiceCategory category2 = new ServiceCategory("House Repairs");
        categoryService.addServiceCategory(category1);
        categoryService.addServiceCategory(category2);

        service1 = Service.of("Service1", 1000, category1);
        service2 = Service.of("Service2", 500, category2);
        serviceManager.addService(service1);
        serviceManager.addService(service2);
    }

    @Test
    @Order(1)
    void addServiceRequest() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2023, 1, 11, 19, 30);
        Date startDate1 = Timestamp.valueOf(localDateTime1);
        ServiceRequest request1 = ServiceRequest.of(customer1, service1, 1000, startDate1);
        ServiceRequest addedRequest1 = requestService.addServiceRequest(request1);
        assertNotNull(addedRequest1);

        LocalDateTime localDateTime2 = LocalDateTime.of(2023, 1, 1, 11, 11);
        Date startDate2 = Timestamp.valueOf(localDateTime2);
        ServiceRequest request2 = ServiceRequest.of(customer2, service2, 600, startDate2);
        ServiceRequest addedRequest2 = requestService.addServiceRequest(request2);
        assertNotNull(addedRequest2);

        ServiceRequest duplicateNewRequest = ServiceRequest.of(customer1, service1, 1000, startDate1);
        assertThrows(Exception.class, () -> requestService.addServiceRequest(duplicateNewRequest));

        LocalDateTime badLocalDAteTime = LocalDateTime.of(2018, 1, 11, 19, 30);
        Date badStartDate = Timestamp.valueOf(badLocalDAteTime);
        ServiceRequest requestWithBadStartDate = ServiceRequest.of(customer1, service1, 1000, badStartDate);
        assertThrows(Exception.class, () -> requestService.addServiceRequest(requestWithBadStartDate));

        ServiceRequest requestWithBadPrice = ServiceRequest.of(customer2, service1, 1, startDate1);
        assertThrows(Exception.class, () -> requestService.addServiceRequest(requestWithBadPrice));
    }
}