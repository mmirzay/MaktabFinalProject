package com.project.my.homeservicessystem.backend.repositories;

import com.project.my.homeservicessystem.backend.entities.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
