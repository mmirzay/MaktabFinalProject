package com.project.my.homeservicessystem.backend.api.dto.in;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFeedbackParam {
    private String text;
    private Long providerId;
    private Long serviceId;
    private int rate;
}
