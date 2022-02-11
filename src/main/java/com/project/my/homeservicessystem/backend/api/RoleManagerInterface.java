package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.in.RoleCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.RoleCreateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.RoleDeleteResult;
import com.project.my.homeservicessystem.backend.api.dto.out.RolesList;

public interface RoleManagerInterface {

    RoleCreateResult addRole(RoleCreateParam createParam);

    RolesList getRolesList();

    RoleDeleteResult deleteRoleById(Long id);
}
