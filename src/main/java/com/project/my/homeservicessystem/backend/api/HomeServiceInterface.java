package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.in.CustomerRegisterParam;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerUpdatePasswordParam;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerUpdateProfileParam;
import com.project.my.homeservicessystem.backend.api.dto.in.RoleCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.*;

public interface HomeServiceInterface {

    RoleCreateResult addRole(RoleCreateParam createParam);

    RolesList getRolesList();

    RoleDeleteResult deleteRoleById(Long id);

    CustomerRegisterResult registerCustomer(CustomerRegisterParam registerParam);

    CustomerProfileResult getCustomerProfile(Long id);

    CustomerUpdateResult updateCustomerProfile(Long id, CustomerUpdateProfileParam param);

    CustomerUpdateResult updateCustomerPassword(Long id, CustomerUpdatePasswordParam param);
}
