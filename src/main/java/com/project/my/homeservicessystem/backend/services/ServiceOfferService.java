package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.repositories.ServiceOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceOfferService {
    private final ServiceOfferRepository repository;
}
