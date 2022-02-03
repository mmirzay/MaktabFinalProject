package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import com.project.my.homeservicessystem.backend.entities.services.ServiceRequest;
import com.project.my.homeservicessystem.backend.entities.services.ServiceRequestStatus;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.exceptions.ServiceRequestException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void setUp() throws Exception {
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
    void addServiceRequest() throws Exception {
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

        LocalDateTime localDateTime3 = LocalDateTime.of(2023, 1, 11, 19, 30);
        Date startDate3 = Timestamp.valueOf(localDateTime3);
        ServiceRequest request3 = ServiceRequest.of(customer1, service2, 1500, startDate3);
        ServiceRequest addedRequest3 = requestService.addServiceRequest(request3);
        assertNotNull(addedRequest3);
    }

    @Test
    @Order(2)
    void getAllServiceRequests() {
        List<ServiceRequest> allServiceRequests = requestService.getAllServiceRequests();
        assertEquals(3, allServiceRequests.size());
    }

    @Test
    @Order(3)
    void getRequestsOfService() {
        List<ServiceRequest> requestsOfService1 = requestService.getRequestsOfService(service1);
        assertEquals(1, requestsOfService1.size());

        List<ServiceRequest> requestsOfService2 = requestService.getRequestsOfService(service2);
        assertEquals(2, requestsOfService2.size());

        Service notExist = new Service();
        notExist.setId(1234L);
        List<ServiceRequest> requestsOfServiceNotExist = requestService.getRequestsOfService(notExist);
        assertEquals(0, requestsOfServiceNotExist.size());
    }

    @Test
    @Order(4)
    void getRequestsOfCustomer() {
        List<ServiceRequest> requestsOfCustomer1 = requestService.getRequestsOfCustomer(customer1);
        assertEquals(2, requestsOfCustomer1.size());

        List<ServiceRequest> requestsOfCustomer2 = requestService.getRequestsOfCustomer(customer2);
        assertEquals(1, requestsOfCustomer2.size());

        Customer notExist = new Customer();
        notExist.setId(1234L);
        List<ServiceRequest> requestsOfCustomerNotExist = requestService.getRequestsOfCustomer(notExist);
        assertEquals(0, requestsOfCustomerNotExist.size());
    }

    @Test
    @Order(5)
    void updateServiceRequest() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 1, 11, 19, 30);
        Date startDate = Timestamp.valueOf(localDateTime);
        ServiceRequest newRequest = ServiceRequest.of(customer2, service1, 2000, startDate);
        ServiceRequest addedNewRequest = requestService.addServiceRequest(newRequest);
        assertNotNull(addedNewRequest);

        newRequest.setAddress("Tehran");
        newRequest.setStatus(ServiceRequestStatus.DONE);
        assertTrue(requestService.updateServiceRequest(newRequest));
        ServiceRequest updated = requestService.getAllServiceRequests().get(3);
        assertEquals(ServiceRequestStatus.DONE, updated.getStatus());

        ServiceRequest invalidId = new ServiceRequest();
        assertFalse(requestService.updateServiceRequest(invalidId));

        invalidId.setId(1234L);
        assertFalse(requestService.updateServiceRequest(invalidId));

    }

    @Test
    @Order(6)
    void getRequestsByStatus() {
        List<ServiceRequest> underOfferingRequests = requestService.getRequestsByStatus(ServiceRequestStatus.UNDER_OFFERING);
        assertEquals(3, underOfferingRequests.size());

        List<ServiceRequest> doneRequests = requestService.getRequestsByStatus(ServiceRequestStatus.DONE);
        assertEquals(1, doneRequests.size());

        List<ServiceRequest> onGoingRequest = requestService.getRequestsByStatus(ServiceRequestStatus.ON_GOING);
        assertEquals(0, onGoingRequest.size());
    }

    @Test
    @Order(7)
    void getRequestsWithStartDateAfter() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2022, 1, 1, 1, 1);
        Date startDate1 = Timestamp.valueOf(localDateTime1);
        List<ServiceRequest> requestsWithStartDateAfter1 = requestService.getRequestsWithStartDateAfter(startDate1);
        assertEquals(4, requestsWithStartDateAfter1.size());

        LocalDateTime localDateTime2 = LocalDateTime.of(2023, 1, 11, 1, 1);
        Date startDate2 = Timestamp.valueOf(localDateTime2);
        List<ServiceRequest> requestsWithStartDateAfter2 = requestService.getRequestsWithStartDateAfter(startDate2);
        assertEquals(3, requestsWithStartDateAfter2.size());

        LocalDateTime localDateTime3 = LocalDateTime.of(2024, 1, 11, 1, 1);
        Date startDate3 = Timestamp.valueOf(localDateTime3);
        List<ServiceRequest> requestsWithStartDateAfter3 = requestService.getRequestsWithStartDateAfter(startDate3);
        assertEquals(1, requestsWithStartDateAfter3.size());

        LocalDateTime localDateTime4 = LocalDateTime.of(2025, 1, 11, 1, 1);
        Date startDate4 = Timestamp.valueOf(localDateTime4);
        List<ServiceRequest> requestsWithStartDateAfter4 = requestService.getRequestsWithStartDateAfter(startDate4);
        assertEquals(0, requestsWithStartDateAfter4.size());
    }

    @Test
    @Order(8)
    void deleteServiceRequestById() throws ServiceRequestException {
        List<ServiceRequest> allServiceRequests = requestService.getAllServiceRequests();
        assertEquals(4, allServiceRequests.size());
        ServiceRequest serviceRequest = allServiceRequests.get(3);
        requestService.deleteServiceRequestById(serviceRequest.getId());

        allServiceRequests = requestService.getAllServiceRequests();
        assertEquals(3, allServiceRequests.size());
    }

}