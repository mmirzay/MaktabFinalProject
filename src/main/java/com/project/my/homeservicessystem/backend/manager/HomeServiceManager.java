package com.project.my.homeservicessystem.backend.manager;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerRegisterParam;
import com.project.my.homeservicessystem.backend.api.dto.in.RoleCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.CustomerRegisterResult;
import com.project.my.homeservicessystem.backend.api.dto.out.RoleCreateResult;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.exceptions.CustomerException;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import com.project.my.homeservicessystem.backend.exceptions.RoleException;
import com.project.my.homeservicessystem.backend.services.CustomerService;
import com.project.my.homeservicessystem.backend.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeServiceManager implements HomeServiceInterface {
    private final CustomerService customerService;
    private final RoleService roleService;

    @Override
    public RoleCreateResult addRole(RoleCreateParam createParam) {
        Role toAdd = createRole(createParam);
        try {
            Role added = roleService.addRole(toAdd);
            return new RoleCreateResult(added.getId());
        } catch (RoleException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public CustomerRegisterResult registerCustomer(CustomerRegisterParam registerParam) {
        try {
            Role role = roleService.getRoleByName(registerParam.getRole().getName());
            if (role == null)
                throw new RoleException("No such role exists.");
            registerParam.setRole(role);
            Customer toRegister = createCustomer(registerParam);
            Customer registered = customerService.addCustomer(toRegister);
            return new CustomerRegisterResult(registered.getId());
        } catch (CustomerException | RoleException e) {
            throw new ManagerException(e);
        }
    }

    private Role createRole(RoleCreateParam createParam) {
        return new Role(createParam.getName());
    }

    private Customer createCustomer(CustomerRegisterParam registerParam) {
        return Customer.of(registerParam.getEmail(),
                registerParam.getPassword(),
                registerParam.getRole(),
                registerParam.getFirstName(),
                registerParam.getLastName());
    }
}
