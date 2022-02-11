package com.project.my.homeservicessystem.backend.manager;

import com.project.my.homeservicessystem.backend.api.ServiceCategoryManagerInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.ServiceCategoryCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.in.ServiceCategoryUpdateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCategoriesList;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCategoryCreateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCategoryDeleteResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCategoryUpdateResult;
import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import com.project.my.homeservicessystem.backend.exceptions.ServiceCategoryException;
import com.project.my.homeservicessystem.backend.services.ServiceCategoryService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceCategoryManagerFacade implements ServiceCategoryManagerInterface {

    private final ServiceCategoryService categoryService;

    @Override
    public ServiceCategoryCreateResult addServiceCategory(ServiceCategoryCreateParam createParam) {
        ServiceCategory toAdd = createServiceCategory(createParam);
        try {
            ServiceCategory added = categoryService.addServiceCategory(toAdd);
            return new ServiceCategoryCreateResult(added.getId());
        } catch (ServiceCategoryException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceCategoriesList getServiceCategoriesList() {
        List<ServiceCategory> categories = categoryService.getAllServiceCategories();
        if (categories.isEmpty())
            throw new ManagerException(new ServiceCategoryException("Categories list is empty"));
        return new ServiceCategoriesList(categories);
    }

    @Override
    public ServiceCategoryUpdateResult updateServiceCategory(Long id, ServiceCategoryUpdateParam param) {
        try {
            ServiceCategory category = categoryService.getServiceCategoryById(id);
            if (categoryService.getServiceCategoryByName(param.getName()) != null)
                throw new ServiceCategoryException("New name is duplicate.");
            category.setName(param.getName());
            String message = categoryService.updateServiceCategory(category) ? "updated successfully." : "updating failed!";
            return new ServiceCategoryUpdateResult(id, message);
        } catch (ServiceCategoryException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceCategoryDeleteResult deleteServiceCategoryById(Long id) {
        try {
            ServiceCategory category = categoryService.getServiceCategoryById(id);
            categoryService.deleteServiceCategoryById(id);
            return new ServiceCategoryDeleteResult(category.getId(), category.getName());
        } catch (ServiceCategoryException e) {
            throw new ManagerException(e);
        }
    }

    private ServiceCategory createServiceCategory(ServiceCategoryCreateParam createParam) {
        return new ServiceCategory(createParam.getName());
    }
}
