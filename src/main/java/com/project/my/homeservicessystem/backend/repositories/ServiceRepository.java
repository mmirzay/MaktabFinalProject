package com.project.my.homeservicessystem.backend.repositories;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findByTitle(String title);
    List<Service> findAllByCategory(ServiceCategory category);
}
