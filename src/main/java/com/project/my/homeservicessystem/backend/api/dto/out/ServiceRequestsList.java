package com.project.my.homeservicessystem.backend.api.dto.out;

import com.project.my.homeservicessystem.backend.entities.services.ServiceRequest;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ServiceRequestsList {
    private final List<ServiceRequest> requests;
}
