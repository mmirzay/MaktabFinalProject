package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.users.Provider;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProviderServiceTest {

    @Autowired
    ProviderService service;

    @Autowired
    RoleService roleService;

    @Test
    @Order(1)
    void addProvider() {
        Role role1 = new Role("Provider");
        Role role2 = new Role("User");
        roleService.addRole(role1);
        roleService.addRole(role2);

        String validEmail1 = "mirzay.mohsen@gmail.com";
        String validPassword1 = "Abcd1234";

        Provider provider = Provider.of(validEmail1, validPassword1, role1);
        Provider addedProvider1 = service.addProvider(provider);
        assertNotNull(addedProvider1);

        Provider duplicateEmail = Provider.of(validEmail1, validPassword1, role1);
        assertThrows(Exception.class, () -> service.addProvider(duplicateEmail));

        String invalidEmail = "@m";
        Provider providerWithInvalidEmail = Provider.of(invalidEmail, validPassword1, role1);
        assertThrows(Exception.class, () -> service.addProvider(providerWithInvalidEmail));

        String validEmail2 = "validEmail2@mail.com";
        String invalidPassword = "1234567";
        Provider providerWithInvalidPassword = Provider.of(validEmail2, invalidPassword, role1);
        assertThrows(Exception.class, () -> service.addProvider(providerWithInvalidPassword));

        String validPassword2 = "Abcd1234";
        Provider provider2 = Provider.of(validEmail2, validPassword2, role1);

        Provider addedProvider2 = service.addProvider(provider2);
        assertNotNull(addedProvider2);
    }

    @Test
    void getAllProviders() {
    }

    @Test
    void getProviderByEmail() {
    }

    @Test
    void updateProvider() {
    }

    @Test
    void deleteProviderById() {
    }
}