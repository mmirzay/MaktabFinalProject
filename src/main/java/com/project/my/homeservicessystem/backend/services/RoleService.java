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

    public Role addRole(Role role) throws RoleException {
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

    public Role getRoleByName(String name) {
        return repository.findByName(name);
    }

    public Role getRoleById(Long id) throws RoleException {
        return repository.findById(id).orElseThrow(() -> new RoleException("Role ID is not exist."));
    }

    public void deleteRoleById(Long id) throws RoleException {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            if (e.getRootCause() instanceof SQLIntegrityConstraintViolationException)
                throw new RoleException("Role is set for at least one user.");
            throw new RoleException("Some thing wrong while deleting role.", e);
        }
    }


}
