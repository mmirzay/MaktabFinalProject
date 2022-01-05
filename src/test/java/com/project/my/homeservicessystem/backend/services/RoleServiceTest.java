package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.users.Role;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleServiceTest {
    @Autowired
    RoleService service;

    @Test
    @Order(1)
    void addRole() {
        Role customer = new Role("customer");

        assertNull(customer.getId());
        Role addedRole = service.addRole(customer);
        assertNotNull(customer.getId());
        assertNotNull(addedRole);
        addedRole.setName("Provider");

        Role provider = new Role("Provider");
        service.addRole(provider);

        Role admin = new Role("Admin");
        service.addRole(admin);

        Role duplicateName = new Role("Admin");
        assertThrows(Exception.class, () -> service.addRole(duplicateName));
    }

    @Test
    @Order(2)
    void getAllRoles() {
        List<Role> allRoles = service.getAllRoles();
        assertEquals(allRoles.size(), 3);
    }

    @Test
    @Order(3)
    void getRoleByName() {
        String name = "customer";
        Role foundByName = service.getRoleByName(name);
        assertNotNull(foundByName);
        Role notFound = service.getRoleByName("somethingNotExist");
        assertNull(notFound);
    }

    @Test
    @Order(4)
    void updateRole() {
        String name = "customer";
        Role foundByName = service.getRoleByName(name);
        assertNotNull(foundByName);
        foundByName.setName("Customer");
        assertTrue(service.updateRole(foundByName));
        Role updated = service.getRoleByName("Customer");
        assertNotNull(updated);
        assertEquals(updated.getName(), "Customer");

        Role invalidId = new Role("Not Exist");
        assertFalse(service.updateRole(invalidId));

        invalidId.setId(1234L);
        assertFalse(service.updateRole(invalidId));
    }

    @Test
    @Order(5)
    void deleteRoleById() {
        Role toRemove = new Role("ToRemove");
        service.addRole(toRemove);
        List<Role> allRoles = service.getAllRoles();
        assertEquals(allRoles.size(), 4);
        assertTrue(service.deleteRoleById(toRemove.getId()));
        allRoles = service.getAllRoles();
        assertEquals(allRoles.size(), 3);
        assertFalse(service.deleteRoleById(1234L));
    }
}