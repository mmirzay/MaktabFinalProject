package com.project.my.homeservicessystem.backend.manager;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerRegisterParam;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerUpdatePasswordParam;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerUpdateProfileParam;
import com.project.my.homeservicessystem.backend.api.dto.in.RoleCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.*;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.exceptions.CustomerException;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import com.project.my.homeservicessystem.backend.exceptions.RoleException;
import com.project.my.homeservicessystem.backend.services.CustomerService;
import com.project.my.homeservicessystem.backend.services.RoleService;
import com.project.my.homeservicessystem.backend.utilities.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public RolesList getRolesList() {
        List<Role> roles = roleService.getAllRoles();
        if (roles.isEmpty())
            throw new ManagerException(new RoleException("Roles list is empty"));
        return new RolesList(roles);
    }

    @Override
    public RoleDeleteResult deleteRoleById(Long id) {
        try {
            Role role = roleService.getRoleById(id);
            roleService.deleteRoleById(id);
            return new RoleDeleteResult(role.getId(), role.getName());
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

    @Override
    public CustomerProfileResult getCustomerProfile(Long id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            return CustomerProfileResult.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .email(customer.getEmail())
                    .registeredDate(customer.getRegisterDate())
                    .credit(customer.getCredit())
                    .status(customer.getStatus())
                    .build();
        } catch (CustomerException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public CustomerUpdateResult updateCustomerProfile(Long id, CustomerUpdateProfileParam param) {
        try {
            Customer customer = customerService.getCustomerById(id);
            customer.setFirstName(param.getFirstName());
            customer.setLastName(param.getLastName());
            String message = customerService.updateCustomer(customer) ? "updated successfully." : "updating failed!";
            return new CustomerUpdateResult(id, message);
        } catch (CustomerException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public CustomerUpdateResult updateCustomerPassword(Long id, CustomerUpdatePasswordParam param) {
        try {
            Customer customer = customerService.getCustomerById(id);
            if (customer.getPassword().equals(param.getOldPassword()) == false)
                throw new CustomerException("Current password is not correct.");
            if (Validator.validatePassword(param.getNewPassword()) == false)
                throw new CustomerException("New password is not valid.");
            customer.setPassword(param.getNewPassword());
            String message = customerService.updateCustomer(customer) ? "updated successfully." : "updating failed!";
            return new CustomerUpdateResult(id, message);
        } catch (CustomerException e) {
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
