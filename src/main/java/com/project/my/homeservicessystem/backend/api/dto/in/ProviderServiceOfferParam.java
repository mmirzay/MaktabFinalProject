package com.project.my.homeservicessystem.backend.api.dto.in;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderServiceOfferParam {
    private int startHour;
    private double price;
    private int durationInHours;
    private Long requestId;
}
