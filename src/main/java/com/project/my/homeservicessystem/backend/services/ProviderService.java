package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.repositories.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderService {
    private final ProviderRepository repository;
}
