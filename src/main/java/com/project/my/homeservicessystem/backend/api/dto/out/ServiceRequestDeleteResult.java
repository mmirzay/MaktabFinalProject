package com.project.my.homeservicessystem.backend.api.dto.out;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServiceRequestDeleteResult {
    private final Long id;
    private final String customerEmail;
}
