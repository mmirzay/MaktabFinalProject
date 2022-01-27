package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.in.*;
import com.project.my.homeservicessystem.backend.api.dto.out.*;

public interface HomeServiceInterface {

    // Roles
    RoleCreateResult addRole(RoleCreateParam createParam);

    RolesList getRolesList();

    RoleDeleteResult deleteRoleById(Long id);


    //Customers
    CustomerRegisterResult registerCustomer(CustomerRegisterParam registerParam);

    CustomerProfileResult getCustomerProfile(Long id);

    CustomerUpdateResult updateCustomerProfile(Long id, CustomerUpdateProfileParam param);

    CustomerUpdateResult updateCustomerPassword(Long id, CustomerUpdatePasswordParam param);


    //ServiceCategories
    ServiceCategoryCreateResult addServiceCategory(ServiceCategoryCreateParam createParam);

    ServiceCategoriesList getServiceCategoriesList();

    ServiceCategoryUpdateResult updateServiceCategory(Long id, ServiceCategoryUpdateParam param);

    ServiceCategoryDeleteResult deleteServiceCategoryById(Long id);
}
