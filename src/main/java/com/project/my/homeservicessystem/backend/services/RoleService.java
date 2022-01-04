package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;
}
