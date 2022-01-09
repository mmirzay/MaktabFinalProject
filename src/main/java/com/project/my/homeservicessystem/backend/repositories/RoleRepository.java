package com.project.my.homeservicessystem.backend.repositories;

import com.project.my.homeservicessystem.backend.entities.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}