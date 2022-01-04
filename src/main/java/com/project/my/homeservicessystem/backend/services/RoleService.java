package com.project.my.homeservicessystem.backend.services;

import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.exceptions.RoleException;
import com.project.my.homeservicessystem.backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public Role addRole(Role role) {
        try {
            return repository.save(role);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException)
                throw new RoleException("Duplicate Role Name");
            throw new RoleException("Some thing wrong while adding new role", e);
        }
    }

    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    public List<Role> getRoleByName(String name) {
        return repository.findByName(name);
    }

    public boolean updateRole(Role role) {
        if (role.getId() == null || repository.findById(role.getId()).isPresent() == false)
            return false;
        repository.save(role);
        return true;
    }

    public boolean deleteRoleById(Long id) {
        if (id == null || repository.findById(id).isPresent() == false)
            return false;
        repository.deleteById(id);
        return true;
    }
}
