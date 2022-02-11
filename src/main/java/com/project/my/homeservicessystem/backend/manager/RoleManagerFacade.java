package com.project.my.homeservicessystem.backend.manager;

import com.project.my.homeservicessystem.backend.api.RoleManagerInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.RoleCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.RoleCreateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.RoleDeleteResult;
import com.project.my.homeservicessystem.backend.api.dto.out.RolesList;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import com.project.my.homeservicessystem.backend.exceptions.RoleException;
import com.project.my.homeservicessystem.backend.services.RoleService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RoleManagerFacade implements RoleManagerInterface {
    private final RoleService roleService;

    @Override
    public RoleCreateResult addRole(RoleCreateParam createParam) {
        Role toAdd = createRole(createParam);
        try {
            Role added = roleService.addRole(toAdd);
            return new RoleCreateResult(added.getId());
        } catch (RoleException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public RolesList getRolesList() {
        List<Role> roles = roleService.getAllRoles();
        if (roles.isEmpty())
            throw new ManagerException(new RoleException("Roles list is empty"));
        return new RolesList(roles);
    }

    @Override
    public RoleDeleteResult deleteRoleById(Long id) {
        try {
            Role role = roleService.getRoleById(id);
            roleService.deleteRoleById(id);
            return new RoleDeleteResult(role.getId(), role.getName());
        } catch (RoleException e) {
            throw new ManagerException(e);
        }
    }

    private Role createRole(RoleCreateParam createParam) {
        return new Role(createParam.getName());
    }
}
