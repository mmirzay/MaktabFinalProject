package com.project.my.homeservicessystem.backend.api.dto.out;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import com.project.my.homeservicessystem.backend.entities.users.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class ProviderProfileResult {
    private String firstName;
    private String lastName;
    private String email;
    private Date registeredDate;
    private UserStatus status;
    private double credit;
    private String profilePhotoUrl;
    private long score;
    private List<Service> services;
}
