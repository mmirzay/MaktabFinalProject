package com.project.my.homeservicessystem.backend.manager;

import com.project.my.homeservicessystem.backend.api.ServiceManagerInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.ServiceCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.in.ServiceUpdateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCreateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceDeleteResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceUpdateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServicesList;
import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.services.ServiceCategory;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import com.project.my.homeservicessystem.backend.exceptions.ServiceCategoryException;
import com.project.my.homeservicessystem.backend.exceptions.ServiceException;
import com.project.my.homeservicessystem.backend.services.ServiceCategoryService;
import com.project.my.homeservicessystem.backend.services.ServiceService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceManagerFacade implements ServiceManagerInterface {

    private final ServiceCategoryService categoryService;
    private final ServiceService serviceService;

    @Override
    public ServiceCreateResult addService(ServiceCreateParam createParam) {
        try {
            ServiceCategory category = categoryService.getServiceCategoryById(createParam.getCategoryId());
            Service toAdd = createService(createParam, category);
            Service added = serviceService.addService(toAdd);
            return new ServiceCreateResult(added.getId());
        } catch (ServiceCategoryException | ServiceException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServicesList getServicesList() {
        List<Service> services = serviceService.getAllServices();
        if (services.isEmpty())
            throw new ManagerException(new ServiceException("Services list is empty"));
        return new ServicesList(services);
    }

    @Override
    public ServiceUpdateResult updateService(Long id, ServiceUpdateParam param) {
        try {
            Service service = serviceService.getServiceById(id);
            ServiceCategory category = categoryService.getServiceCategoryById(param.getCategoryId());
            if (service.getTitle().equals(param.getTitle()) == false
                    && serviceService.getServiceByTitle(param.getTitle()) != null)
                throw new ServiceException("New title is duplicate.");
            if (param.getBasePrice() < 0)
                throw new ServiceException("Base price is invalid");
            service.setTitle(param.getTitle());
            service.setBasePrice(param.getBasePrice());
            service.setDescription(param.getDescription());
            service.setCategory(category);
            String message = serviceService.updateService(service) ? "updated successfully." : "updating failed!";
            return new ServiceUpdateResult(id, message);
        } catch (ServiceCategoryException | ServiceException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceDeleteResult deleteServiceById(Long id) {
        try {
            Service service = serviceService.getServiceById(id);
            serviceService.deleteServiceById(id);
            return new ServiceDeleteResult(service.getId(), service.getTitle());
        } catch (ServiceException e) {
            throw new ManagerException(e);
        }
    }

    private Service createService(ServiceCreateParam createParam, ServiceCategory category) {
        return Service.of(createParam.getTitle(), createParam.getBasePrice(), createParam.getDescription(), category);
    }
}
