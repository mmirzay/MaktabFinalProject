package com.project.my.homeservicessystem.backend.api.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderRegisterParam {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long roleId;
}
