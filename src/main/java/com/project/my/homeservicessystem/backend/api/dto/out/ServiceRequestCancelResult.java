package com.project.my.homeservicessystem.backend.api.dto.out;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServiceRequestCancelResult {
    private final Long id;
    private final String message;
}
