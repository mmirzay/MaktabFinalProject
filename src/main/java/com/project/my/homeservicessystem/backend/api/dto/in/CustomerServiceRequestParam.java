package com.project.my.homeservicessystem.backend.api.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerServiceRequestParam {
    private double price;
    private String description;
    private String address;
    private Date startDate;
    private Long serviceId;
}
