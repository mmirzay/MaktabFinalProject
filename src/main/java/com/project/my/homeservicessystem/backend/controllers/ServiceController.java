package com.project.my.homeservicessystem.backend.controllers;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.ServiceCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.in.ServiceUpdateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCreateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceDeleteResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceUpdateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServicesList;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/services")
public class ServiceController {
    private final HomeServiceInterface manager;

    @PostMapping
    public ResponseEntity<ServiceCreateResult> create(@RequestBody ServiceCreateParam createParam) {
        try {
            ServiceCreateResult result = manager.addService(createParam);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ServicesList> servicesList() {
        try {
            ServicesList result = manager.getServicesList();
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ServiceUpdateResult> update(@PathVariable Long id, @RequestBody ServiceUpdateParam param) {
        try {
            ServiceUpdateResult result = manager.updateService(id, param);
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ServiceDeleteResult> remove(@PathVariable Long id) {
        try {
            ServiceDeleteResult result = manager.deleteServiceById(id);
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

}
