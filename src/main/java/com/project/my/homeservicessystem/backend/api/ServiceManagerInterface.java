package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.in.ServiceCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.in.ServiceUpdateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceCreateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceDeleteResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServiceUpdateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ServicesList;

public interface ServiceManagerInterface {

    ServiceCreateResult addService(ServiceCreateParam createParam);

    ServicesList getServicesList();

    ServiceUpdateResult updateService(Long id, ServiceUpdateParam param);

    ServiceDeleteResult deleteServiceById(Long id);
}
