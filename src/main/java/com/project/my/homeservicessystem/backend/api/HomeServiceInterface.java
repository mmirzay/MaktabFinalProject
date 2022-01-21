package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.in.CustomerRegisterParam;
import com.project.my.homeservicessystem.backend.api.dto.in.RoleCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.CustomerRegisterResult;
import com.project.my.homeservicessystem.backend.api.dto.out.RoleCreateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.RoleDeleteResult;
import com.project.my.homeservicessystem.backend.api.dto.out.RolesList;

public interface HomeServiceInterface {

    RoleCreateResult addRole(RoleCreateParam createParam);

    RolesList getRolesList();

    RoleDeleteResult deleteRoleById(Long id);

    CustomerRegisterResult registerCustomer(CustomerRegisterParam registerParam);

}
