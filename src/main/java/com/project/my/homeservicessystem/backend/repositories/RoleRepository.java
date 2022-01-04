package com.project.my.homeservicessystem.backend.repositories;

import com.project.my.homeservicessystem.backend.entities.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
