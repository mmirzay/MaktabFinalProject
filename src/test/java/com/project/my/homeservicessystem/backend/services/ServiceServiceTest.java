package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceServiceTest {
    @Autowired
    ServiceService serviceManager;

    @Autowired
    ServiceCategoryService categoryService;

    private ServiceCategory category1;
    private ServiceCategory category2;

    @BeforeAll
    void setUp() {
        category1 = new ServiceCategory("House Cleaning");
        category2 = new ServiceCategory("House Repairs");
        categoryService.addServiceCategory(category1);
        categoryService.addServiceCategory(category2);
    }

    @Test
    @Order(1)
    void addService() {
        Service service1 = Service.of("Service1", category1);
        Service addedService1 = serviceManager.addService(service1);
        assertNotNull(addedService1);

        Service service2 = Service.of("Service2", category2);
        Service addedService2 = serviceManager.addService(service2);
        assertNotNull(addedService2);

        Service duplicate = Service.of("Service1", category2);
        assertThrows(Exception.class, () -> serviceManager.addService(duplicate));

        Service invalidBasePrice = Service.of("InvalidBasePrice", -1, category1);
        assertThrows(Exception.class, () -> serviceManager.addService(invalidBasePrice));

        Service service3 = Service.of("Service3", category1);
        Service service4 = Service.of("Service4", category2);
        Service service5 = Service.of("Service5", category1);
        assertNotNull(serviceManager.addService(service3));
        assertNotNull(serviceManager.addService(service4));
        assertNotNull(serviceManager.addService(service5));
    }

    @Test
    @Order(2)
    void getAllServices() {
        List<Service> allServices = serviceManager.getAllServices();
        assertEquals(5, allServices.size());
    }

    @Test
    @Order(3)
    void getAllServicesByCategory() {
        ServiceCategory category1 = categoryService.getAllServiceCategories().get(0);
        List<Service> allServicesByCategory1 = serviceManager.getAllServicesByCategory(category1);
        assertEquals(3, allServicesByCategory1.size());

        ServiceCategory category2 = categoryService.getAllServiceCategories().get(1);
        List<Service> allServicesByCategory2 = serviceManager.getAllServicesByCategory(category2);
        assertEquals(2, allServicesByCategory2.size());
    }

    @Test
    @Order(4)
    void getServiceByTitle() {
        Service found = serviceManager.getServiceByTitle("Service1");
        assertNotNull(found);
        assertNull(serviceManager.getServiceByTitle("NotExist"));
    }

    @Test
    @Order(5)
    void updateService() {
        Service found = serviceManager.getServiceByTitle("Service1");
        assertNotNull(found);
        found.setBasePrice(1000);
        found.setDescription("Service1 desription");
        assertTrue(serviceManager.updateService(found));
        Service updated = serviceManager.getServiceByTitle("Service1");
        assertEquals(updated.getBasePrice(), 1000);

        Service invalidId = Service.of("InvalidId", category1);
        assertFalse(serviceManager.updateService(invalidId));

        invalidId.setId(1234L);
        assertFalse(serviceManager.updateService(invalidId));
    }

    @Test
    @Order(6)
    void deleteServiceById() {
        Service toRemove = serviceManager.getServiceByTitle("Service5");
        assertTrue(serviceManager.deleteServiceById(toRemove.getId()));
        assertEquals(4, serviceManager.getAllServices().size());
        ServiceCategory category1 = categoryService.getAllServiceCategories().get(0);
        assertEquals(2, serviceManager.getAllServicesByCategory(category1).size());
        assertFalse(serviceManager.deleteServiceById(toRemove.getId()));
    }
}