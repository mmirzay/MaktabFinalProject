package com.project.my.homeservicessystem.backend.api.dto.in;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCreateParam {
    private String title;
    private double basePrice;
    private String description;
    private Long categoryId;
}
