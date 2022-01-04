package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.repositories.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {
    private final ServiceRequestRepository repository;
}
