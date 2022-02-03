package com.project.my.homeservicessystem.backend.api.dto.out;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerDeleteResult {
    private final Long id;
    private final String email;
}
