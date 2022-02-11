package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.in.ServiceCategoryCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.in.ServiceCategoryUpdateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCategoriesList;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCategoryCreateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCategoryDeleteResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCategoryUpdateResult;

public interface ServiceCategoryManagerInterface {

    ServiceCategoryCreateResult addServiceCategory(ServiceCategoryCreateParam createParam);

    ServiceCategoriesList getServiceCategoriesList();

    ServiceCategoryUpdateResult updateServiceCategory(Long id, ServiceCategoryUpdateParam param);

    ServiceCategoryDeleteResult deleteServiceCategoryById(Long id);
}
