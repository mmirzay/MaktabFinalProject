package com.project.my.homeservicessystem.backend.repositories;

import com.project.my.homeservicessystem.backend.entities.users.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
}
