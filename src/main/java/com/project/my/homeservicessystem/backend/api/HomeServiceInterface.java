package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.in.RoleCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.RoleCreateResult;

public interface HomeServiceInterface {
    RoleCreateResult addRole(RoleCreateParam createParam);
}
