package com.project.my.homeservicessystem.backend.api.dto.in;

import com.project.my.homeservicessystem.backend.entities.users.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterParam {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
