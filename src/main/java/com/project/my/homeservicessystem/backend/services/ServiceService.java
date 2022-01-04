package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository repository;
}
