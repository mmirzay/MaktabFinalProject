package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.users.Provider;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.exceptions.ProviderException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProviderServiceTest {

    @Autowired
    ProviderService service;

    @Autowired
    RoleService roleService;

    private Role role1;
    private Role role2;

    @BeforeAll
    void setUp() throws Exception {
        role1 = new Role("Provider");
        role2 = new Role("User");
        roleService.addRole(role1);
        roleService.addRole(role2);
    }

    @Test
    @Order(1)
    void addProvider() throws Exception {

        String validEmail1 = "mirzay.mohsen@gmail.com";
        String validPassword1 = "Abcd1234";

        Provider provider = Provider.of(validEmail1, validPassword1, role1);
        provider.getRoles().add(role2);
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
    @Order(2)
    void getAllProviders() {
        List<Provider> allProviders = service.getAllProviders();
        assertEquals(2, allProviders.size());
    }

    @Test
    @Order(3)
    void getProviderByEmail() {
        Provider found = service.getProviderByEmail("mirzay.mohsen@gmail.com");
        assertNotNull(found);

        Provider notFound = service.getProviderByEmail("some@email.com");
        assertNull(notFound);
    }

    @Test
    @Order(4)
    void updateProvider() {
        String email = "mirzay.mohsen@gmail.com";
        Provider found = service.getProviderByEmail(email);
        assertNotNull(found);

        found.setFirstName("Mohsen");
        found.setLastName("Mirzaei");
        found.setScore(10);
        assertTrue(service.updateProvider(found));
        Provider updated = service.getProviderByEmail(email);
        assertEquals(updated.getFirstName(), "Mohsen");
        assertEquals(updated.getScore(), 10);
    }

    @Test
    @Order(5)
    void deleteProviderById() throws ProviderException {
        Provider toRemove = service.getProviderByEmail("validEmail2@mail.com");
        assertNotNull(toRemove);
        service.deleteProviderById(toRemove.getId());
        assertEquals(service.getAllProviders().size(), 1);
    }
}