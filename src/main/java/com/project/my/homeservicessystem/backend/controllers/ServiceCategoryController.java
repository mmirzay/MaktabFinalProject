package com.project.my.homeservicessystem.backend.controllers;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.RoleCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.in.ServiceCategoryCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.in.ServiceCategoryUpdateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.*;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service-categories")
public class ServiceCategoryController {
    private final HomeServiceInterface manager;

    @PostMapping
    public ResponseEntity<ServiceCategoryCreateResult> create(@RequestBody ServiceCategoryCreateParam createParam) {
        try {
            ServiceCategoryCreateResult result = manager.addServiceCategory(createParam);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ServiceCategoriesList> serviceCategoriesList() {
        try {
            ServiceCategoriesList result = manager.getServiceCategoriesList();
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ServiceCategoryUpdateResult> update(@PathVariable Long id, @RequestBody ServiceCategoryUpdateParam param) {
        try {
            ServiceCategoryUpdateResult result = manager.updateServiceCategory(id, param);
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ServiceCategoryDeleteResult> remove(@PathVariable Long id) {
        try {
            ServiceCategoryDeleteResult result = manager.deleteServiceCategoryById(id);
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

}
