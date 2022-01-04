package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.repositories.ServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceCategoryService {
    private final ServiceCategoryRepository repository;
}
