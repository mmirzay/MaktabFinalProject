package com.project.my.homeservicessystem.backend.api.dto.out;

import com.project.my.homeservicessystem.backend.entities.services.ServiceRequest;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ServiceRequestsList {
    private List<ServiceRequest> requests;
}
