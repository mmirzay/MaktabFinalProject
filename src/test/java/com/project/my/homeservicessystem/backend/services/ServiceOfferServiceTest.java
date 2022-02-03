package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import com.project.my.homeservicessystem.backend.entities.services.ServiceOffer;
import com.project.my.homeservicessystem.backend.entities.services.ServiceRequest;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Provider;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.exceptions.ServiceOfferException;
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
class ServiceOfferServiceTest {

    @Autowired
    ServiceOfferService offerService;

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

    @Autowired
    ServiceRequestService requestService;

    private Provider provider1;
    private Provider provider2;

    private ServiceRequest request1;
    private ServiceRequest request2;
    private ServiceRequest request3;

    @BeforeAll
    void setUp() throws Exception {
        Role role1 = new Role("Provider");
        Role role2 = new Role("Customer");
        roleService.addRole(role1);
        roleService.addRole(role2);

        provider1 = Provider.of("provider1@provider.com", "Abcd1234", role1);
        provider2 = Provider.of("provider2@provider.com", "Abcd1234", role1);
        providerService.addProvider(provider1);
        providerService.addProvider(provider2);

        Customer customer1 = Customer.of("mirzay.mohsen@gmail.com", "Abcd1234", role2);
        Customer customer2 = Customer.of("mmirzay@ut.ac.ir", "Abcd1234", role2);
        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);

        ServiceCategory category1 = new ServiceCategory("House Repairs");
        ServiceCategory category2 = new ServiceCategory("House Cleaning");
        categoryService.addServiceCategory(category1);
        categoryService.addServiceCategory(category2);

        Service service1 = Service.of("Service1", 1000, category1);
        Service service2 = Service.of("Service2", 2000, category2);
        serviceManager.addService(service1);
        serviceManager.addService(service2);

        LocalDateTime localDateTime1 = LocalDateTime.of(2023, 1, 11, 19, 30);
        Date startDate1 = Timestamp.valueOf(localDateTime1);
        request1 = ServiceRequest.of(customer1, service1, 1000, startDate1);

        LocalDateTime localDateTime2 = LocalDateTime.of(2023, 1, 1, 11, 11);
        Date startDate2 = Timestamp.valueOf(localDateTime2);
        request2 = ServiceRequest.of(customer2, service2, 2600, startDate2);

        LocalDateTime localDateTime3 = LocalDateTime.of(2023, 1, 11, 19, 30);
        Date startDate3 = Timestamp.valueOf(localDateTime3);
        request3 = ServiceRequest.of(customer1, service2, 2500, startDate3);

        requestService.addServiceRequest(request1);
        requestService.addServiceRequest(request2);
        requestService.addServiceRequest(request3);
    }

    @Test
    @Order(1)
    void addServiceOffer() throws Exception {
        ServiceOffer offer1 = ServiceOffer.of(provider1, request1, 1000);
        ServiceOffer offer2 = ServiceOffer.of(provider2, request2, 2000);

        ServiceOffer addedOffer1 = offerService.addServiceOffer(offer1);
        assertNotNull(addedOffer1);

        ServiceOffer addedOffer2 = offerService.addServiceOffer(offer2);
        assertNotNull(addedOffer2);

        ServiceOffer duplicateOffer = ServiceOffer.of(provider1, request1, 3000);
        assertThrows(Exception.class, () -> offerService.addServiceOffer(duplicateOffer));

        ServiceOffer invalidPrice = ServiceOffer.of(provider1, request1, 30);
        assertThrows(Exception.class, () -> offerService.addServiceOffer(invalidPrice));

        ServiceOffer offer3 = ServiceOffer.of(provider1, request2, 3000);
        assertNotNull(offerService.addServiceOffer(offer3));

        ServiceOffer offer4 = ServiceOffer.of(provider1, request3, 2500);
        assertNotNull(offerService.addServiceOffer(offer4));

    }

    @Test
    @Order(2)
    void getAllServiceOffers() {
        List<ServiceOffer> allServiceOffers = offerService.getAllServiceOffers();
        assertEquals(4, allServiceOffers.size());
    }

    @Test
    @Order(3)
    void getOffersOfProvider() {
        List<ServiceOffer> offersOfProvider1 = offerService.getOffersOfProvider(provider1);
        assertEquals(3, offersOfProvider1.size());

        List<ServiceOffer> offersOfProvider2 = offerService.getOffersOfProvider(provider2);
        assertEquals(1, offersOfProvider2.size());

        Provider notExist = new Provider();
        notExist.setId(1234L);
        List<ServiceOffer> offersOfNotExist = offerService.getOffersOfProvider(notExist);
        assertEquals(0, offersOfNotExist.size());
    }

    @Test
    @Order(4)
    void getOffersOfServiceRequest() {
        List<ServiceOffer> offersOfServiceRequest1 = offerService.getOffersOfServiceRequest(request1);
        assertEquals(1, offersOfServiceRequest1.size());

        List<ServiceOffer> offersOfServiceRequest2 = offerService.getOffersOfServiceRequest(request2);
        assertEquals(2, offersOfServiceRequest2.size());

        List<ServiceOffer> offersOfServiceRequest3 = offerService.getOffersOfServiceRequest(request3);
        assertEquals(1, offersOfServiceRequest3.size());

        ServiceRequest notExist = new ServiceRequest();
        notExist.setId(1234L);
        List<ServiceOffer> offersOfNotExist = offerService.getOffersOfServiceRequest(notExist);
        assertEquals(0, offersOfNotExist.size());

    }

    @Test
    @Order(5)
    void getAllServiceOffersOrderByPrice() {
        List<ServiceOffer> orderedOffers = offerService.getAllServiceOffersOrderByPrice();
        assertEquals(1000, orderedOffers.get(0).getPrice());
    }

    @Test
    @Order(6)
    void updateServiceOffer() throws Exception {
        ServiceOffer newOffer = ServiceOffer.of(provider2, request1, 4000);
        assertNotNull(offerService.addServiceOffer(newOffer));

        newOffer.setDurationInHours(10);
        newOffer.setStartHour(14);
        assertTrue(offerService.updateServiceOffer(newOffer));

        ServiceOffer updated = offerService.getAllServiceOffers().get(4);
        assertEquals(10, updated.getDurationInHours());
        assertEquals(14, updated.getStartHour());

        ServiceOffer invalidId = new ServiceOffer();
        assertFalse(offerService.updateServiceOffer(invalidId));

        invalidId.setId(1234L);
        assertFalse(offerService.updateServiceOffer(invalidId));
    }

    @Test
    @Order(7)
    void deleteServiceOfferById() throws ServiceOfferException {
        List<ServiceOffer> allServiceOffers = offerService.getAllServiceOffers();
        assertEquals(5, allServiceOffers.size());

        ServiceOffer toRemove = allServiceOffers.get(4);
        offerService.deleteServiceOfferById(toRemove.getId());
        assertEquals(4, offerService.getAllServiceOffers().size());
    }

}