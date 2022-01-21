package com.project.my.homeservicessystem.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.my.homeservicessystem.backend.api.dto.in.RoleCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.RolesList;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @Order(1)
    void create() throws Exception {
        RoleCreateParam adminRole = new RoleCreateParam("Admin");
        mvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(adminRole)))
                .andExpect(status().isCreated());

        RoleCreateParam userRole = new RoleCreateParam("User");
        mvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userRole)))
                .andExpect(status().isCreated());

        mvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userRole)))
                .andExpect(status().isBadRequest())
                .andExpect(r -> Assertions.assertEquals(r.getResponse().getErrorMessage(), "Duplicate Role Name"));
    }

    @Test
    @Order(2)
    void rolesList() throws Exception {
        mvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andDo(r -> {
                    RolesList rolesList = fromJson(r.getResponse().getContentAsString(), RolesList.class);
                    Assertions.assertEquals(2, rolesList.getRoles().size());
                });
    }

    @Test
    @Order(3)
    void remove() throws Exception {
        mvc.perform(delete("/roles/1"))
                .andExpect(status().isOk());

        mvc.perform(delete("/roles/1"))
                .andExpect(status().isBadRequest())
                .andExpect(r -> Assertions.assertEquals(r.getResponse().getErrorMessage(), "Role ID is not exist."));
    }

    protected String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}