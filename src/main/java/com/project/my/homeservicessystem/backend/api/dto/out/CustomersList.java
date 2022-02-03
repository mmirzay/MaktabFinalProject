package com.project.my.homeservicessystem.backend.api.dto.out;

import com.project.my.homeservicessystem.backend.entities.users.Customer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomersList {
    private final List<Customer> customers;
}
