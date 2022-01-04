package com.project.my.homeservicessystem.backend.repositories;

import com.project.my.homeservicessystem.backend.entities.services.ServiceOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOfferRepository extends JpaRepository<ServiceOffer,Long> {
}
