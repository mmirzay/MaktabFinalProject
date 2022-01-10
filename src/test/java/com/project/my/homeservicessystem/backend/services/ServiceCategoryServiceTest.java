package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceCategoryServiceTest {

    @Autowired
    ServiceCategoryService service;

    @Test
    @Order(1)
    void addServiceCategory() {
        ServiceCategory category1 = new ServiceCategory("Home Cleaning");
        assertNull(category1.getId());
        ServiceCategory addedCategory = service.addServiceCategory(category1);
        assertNotNull(addedCategory.getId());

        ServiceCategory category2 = new ServiceCategory("Home Repairs");
        service.addServiceCategory(category2);

        ServiceCategory duplicateName = new ServiceCategory(category2.getName());
        assertThrows(Exception.class, () -> service.addServiceCategory(duplicateName));
    }

    @Test
    @Order(2)
    void getAllServiceCategories() {
        List<ServiceCategory> allServiceCategories = service.getAllServiceCategories();
        assertEquals(2, allServiceCategories.size());

    }

    @Test
    @Order(3)
    void getServiceCategoryByName() {
        String name = "Home Repairs";
        ServiceCategory found = service.getServiceCategoryByName(name);
        assertNotNull(found);
        ServiceCategory notExist = service.getServiceCategoryByName("notExist");
        assertNull(notExist);
    }

    @Test
    @Order(4)
    void updateServiceCategory() {
        String name = "Home Cleaning";
        ServiceCategory found = service.getServiceCategoryByName(name);
        assertNotNull(found);
        found.setName("House Cleaning");
        assertTrue(service.updateServiceCategory(found));
        ServiceCategory updated = service.getServiceCategoryByName("House Cleaning");
        assertNotNull(updated);
        assertEquals(updated.getName(), "House Cleaning");

        ServiceCategory invalidId = new ServiceCategory("InvalidId");
        assertFalse(service.updateServiceCategory(invalidId));

        invalidId.setId(1234L);
        assertFalse(service.updateServiceCategory(invalidId));
    }

    @Test
    @Order(5)
    void deleteServiceCategoryById() {
        ServiceCategory toRemove = new ServiceCategory("ToRemove");
        service.addServiceCategory(toRemove);
        List<ServiceCategory> allServiceCategories = service.getAllServiceCategories();
        assertEquals(3, allServiceCategories.size());
        assertTrue(service.deleteServiceCategoryById(toRemove.getId()));
        allServiceCategories = service.getAllServiceCategories();
        assertEquals(2, allServiceCategories.size());
        assertFalse(service.deleteServiceCategoryById(1234L));
    }
}