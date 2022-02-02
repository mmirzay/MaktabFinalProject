package com.project.my.homeservicessystem.backend.api.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderUpdatePasswordParam {
    private String oldPassword;
    private String newPassword;
}
