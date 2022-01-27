package com.project.my.homeservicessystem.backend.controllers;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerRegisterParam;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerUpdatePasswordParam;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerUpdateProfileParam;
import com.project.my.homeservicessystem.backend.api.dto.out.CustomerProfileResult;
import com.project.my.homeservicessystem.backend.api.dto.out.CustomerRegisterResult;
import com.project.my.homeservicessystem.backend.api.dto.out.CustomerUpdateResult;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final HomeServiceInterface manager;

    @PostMapping("/register")
    public ResponseEntity<CustomerRegisterResult> register(@RequestBody CustomerRegisterParam registerParam) {
        try {
            CustomerRegisterResult result = manager.registerCustomer(registerParam);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerProfileResult> getProfile(@PathVariable Long id) {
        try {
            CustomerProfileResult result = manager.getCustomerProfile(id);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerUpdateResult> updateProfile(@PathVariable Long id, @RequestBody CustomerUpdateProfileParam param) {
        try {
            CustomerUpdateResult result = manager.updateCustomerProfile(id, param);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<CustomerUpdateResult> updatePassword(@PathVariable Long id, @RequestBody CustomerUpdatePasswordParam param) {
        try {
            CustomerUpdateResult result = manager.updateCustomerPassword(id, param);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }
}
