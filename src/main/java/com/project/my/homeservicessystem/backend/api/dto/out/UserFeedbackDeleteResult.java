package com.project.my.homeservicessystem.backend.api.dto.out;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserFeedbackDeleteResult {
    private final Long id;
    private final String customerEmail;
    private final String providerEmail;
}
