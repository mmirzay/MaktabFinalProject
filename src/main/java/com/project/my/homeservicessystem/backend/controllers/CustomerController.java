package com.project.my.homeservicessystem.backend.controllers;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerRegisterParam;
import com.project.my.homeservicessystem.backend.api.dto.out.CustomerRegisterResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final HomeServiceInterface manager;

    @PostMapping
    public ResponseEntity<CustomerRegisterResult> register(@RequestBody CustomerRegisterParam registerParam) {
        CustomerRegisterResult result = manager.registerCustomer(registerParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
