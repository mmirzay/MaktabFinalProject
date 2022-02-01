package com.project.my.homeservicessystem.backend.manager;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.*;
import com.project.my.homeservicessystem.backend.api.dto.out.*;
import com.project.my.homeservicessystem.backend.entities.services.*;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Provider;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.exceptions.*;
import com.project.my.homeservicessystem.backend.services.*;
import com.project.my.homeservicessystem.backend.utilities.Validator;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class HomeServiceManager implements HomeServiceInterface {
    private final RoleService roleService;
    private final CustomerService customerService;
    private final ProviderService providerService;
    private final ServiceCategoryService categoryService;
    private final ServiceService serviceService;
    private final ServiceRequestService requestService;
    private final ServiceOfferService offerService;

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
            Role role = roleService.getRoleById(registerParam.getRoleId());
            Customer toRegister = createCustomer(registerParam, role);
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

    @Override
    public ServiceCategoryCreateResult addServiceCategory(ServiceCategoryCreateParam createParam) {
        ServiceCategory toAdd = createServiceCategory(createParam);
        try {
            ServiceCategory added = categoryService.addServiceCategory(toAdd);
            return new ServiceCategoryCreateResult(added.getId());
        } catch (ServiceCategoryException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceCategoriesList getServiceCategoriesList() {
        List<ServiceCategory> categories = categoryService.getAllServiceCategories();
        if (categories.isEmpty())
            throw new ManagerException(new ServiceCategoryException("Categories list is empty"));
        return new ServiceCategoriesList(categories);
    }

    @Override
    public ServiceCategoryUpdateResult updateServiceCategory(Long id, ServiceCategoryUpdateParam param) {
        try {
            ServiceCategory category = categoryService.getServiceCategoryById(id);
            if (categoryService.getServiceCategoryByName(param.getName()) != null)
                throw new ServiceCategoryException("New name is duplicate.");
            category.setName(param.getName());
            String message = categoryService.updateServiceCategory(category) ? "updated successfully." : "updating failed!";
            return new ServiceCategoryUpdateResult(id, message);
        } catch (ServiceCategoryException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceCategoryDeleteResult deleteServiceCategoryById(Long id) {
        try {
            ServiceCategory category = categoryService.getServiceCategoryById(id);
            categoryService.deleteServiceCategoryById(id);
            return new ServiceCategoryDeleteResult(category.getId(), category.getName());
        } catch (ServiceCategoryException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceCreateResult addService(ServiceCreateParam createParam) {
        try {
            ServiceCategory category = categoryService.getServiceCategoryById(createParam.getCategoryId());
            Service toAdd = createService(createParam, category);
            Service added = serviceService.addService(toAdd);
            return new ServiceCreateResult(added.getId());
        } catch (ServiceCategoryException | ServiceException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServicesList getServicesList() {
        List<Service> services = serviceService.getAllServices();
        if (services.isEmpty())
            throw new ManagerException(new ServiceException("Services list is empty"));
        return new ServicesList(services);
    }

    @Override
    public ServiceUpdateResult updateService(Long id, ServiceUpdateParam param) {
        try {
            Service service = serviceService.getServiceById(id);
            ServiceCategory category = categoryService.getServiceCategoryById(param.getCategoryId());
            if (service.getTitle().equals(param.getTitle()) == false
                    && serviceService.getServiceByTitle(param.getTitle()) != null)
                throw new ServiceException("New title is duplicate.");
            if (param.getBasePrice() < 0)
                throw new ServiceException("Base price is invalid");
            service.setTitle(param.getTitle());
            service.setBasePrice(param.getBasePrice());
            service.setDescription(param.getDescription());
            service.setCategory(category);
            String message = serviceService.updateService(service) ? "updated successfully." : "updating failed!";
            return new ServiceUpdateResult(id, message);
        } catch (ServiceCategoryException | ServiceException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceDeleteResult deleteServiceById(Long id) {
        try {
            Service service = serviceService.getServiceById(id);
            serviceService.deleteServiceById(id);
            return new ServiceDeleteResult(service.getId(), service.getTitle());
        } catch (ServiceException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public CustomerServiceRequestResult requestServiceByCustomer(Long id, CustomerServiceRequestParam param) {
        try {
            Customer customer = customerService.getCustomerById(id);
            Service service = serviceService.getServiceById(param.getServiceId());
            ServiceRequest newRequest = createServiceRequest(param, customer, service);
            ServiceRequest added = requestService.addServiceRequest(newRequest);
            return new CustomerServiceRequestResult(added.getId());
        } catch (CustomerException | ServiceException | ServiceRequestException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceRequestsList getCustomerRequests(Long id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            List<ServiceRequest> requests = requestService.getRequestsOfCustomer(customer);
            if (requests.isEmpty())
                throw new CustomerException("Requests list is empty.");
            return new ServiceRequestsList(requests);
        } catch (CustomerException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceRequestCancelResult cancelServiceRequestByCustomer(Long customerId, Long reqId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            ServiceRequest request = requestService.getRequestById(reqId);
            if (request.getCustomer().getId().equals(customer.getId()) == false)
                throw new ServiceRequestException("Request is not belong to customer");
            if (request.getStatus() == ServiceRequestStatus.DONE ||
                    request.getStatus() == ServiceRequestStatus.PAID ||
                    request.getStatus() == ServiceRequestStatus.CANCELED)
                throw new ServiceRequestException("Request can not be canceled due to its status");
            request.setStatus(ServiceRequestStatus.CANCELED);
            String message = requestService.updateServiceRequest(request) ? "Canceled successfully" : "Canceling failed.";
            return new ServiceRequestCancelResult(reqId, message);
        } catch (CustomerException | ServiceRequestException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceOffersList getOffersOfCustomerRequest(Long customerId, Long reqId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            ServiceRequest request = requestService.getRequestById(reqId);
            if (request.getCustomer().getId().equals(customer.getId()) == false)
                throw new ServiceRequestException("Request is not belong to customer");
            List<ServiceOffer> offers = offerService.getOffersOfServiceRequest(request);
            if (offers.isEmpty())
                throw new CustomerException("Offers list is empty.");
            return new ServiceOffersList(offers);
        } catch (CustomerException | ServiceRequestException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceOfferAcceptResult acceptServiceOfferByCustomer(Long customerId, Long reqId, Long offerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);

            ServiceRequest request = requestService.getRequestById(reqId);
            if (request.getCustomer().getId().equals(customer.getId()) == false)
                throw new ServiceRequestException("Request is not belong to customer");
            if (request.getStatus() != ServiceRequestStatus.UNDER_SELECTION)
                throw new ServiceRequestException("Request can not be accepted due to its status");

            ServiceOffer offer = offerService.getOfferById(offerId);
            if (offer.getRequest().getId().equals(request.getId()) == false)
                throw new ServiceOfferException("Offer is not belong to request");
            request.setStatus(ServiceRequestStatus.ON_GOING);
            String message = requestService.updateServiceRequest(request) ? "Accepted successfully" : "Accepting failed";
            return new ServiceOfferAcceptResult(offerId, message);
        } catch (CustomerException | ServiceRequestException | ServiceOfferException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceOfferPayResult payServiceOfferByCustomer(Long customerId, Long reqId, Long offerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);

            ServiceRequest request = requestService.getRequestById(reqId);
            if (request.getCustomer().getId().equals(customer.getId()) == false)
                throw new ServiceRequestException("Request is not belong to customer");
            if (request.getStatus() != ServiceRequestStatus.DONE)
                throw new ServiceRequestException("Request must be done to pay");

            ServiceOffer offer = offerService.getOfferById(offerId);
            if (offer.getRequest().getId().equals(request.getId()) == false)
                throw new ServiceOfferException("Offer is not belong to request");

            if (customer.getCredit() < offer.getPrice())
                throw new CustomerException("Customer credit is not enough to pay");
            Provider provider = offer.getProvider();
            provider.setCredit(provider.getCredit() + offer.getPrice());
            customer.setCredit(customer.getCredit() - offer.getPrice());
            customerService.updateCustomer(customer);
            providerService.updateProvider(provider);
            request.setStatus(ServiceRequestStatus.PAID);
            String message = requestService.updateServiceRequest(request) ? "Accepted successfully" : "Accepting failed";
            return new ServiceOfferPayResult(offerId, message);
        } catch (CustomerException | ServiceRequestException | ServiceOfferException e) {
            throw new ManagerException(e);
        }
    }

    private Role createRole(RoleCreateParam createParam) {
        return new Role(createParam.getName());
    }

    private Customer createCustomer(CustomerRegisterParam registerParam, Role role) {
        return Customer.of(registerParam.getEmail(),
                registerParam.getPassword(),
                role,
                registerParam.getFirstName(),
                registerParam.getLastName());
    }

    private ServiceCategory createServiceCategory(ServiceCategoryCreateParam createParam) {
        return new ServiceCategory(createParam.getName());
    }

    private Service createService(ServiceCreateParam createParam, ServiceCategory category) {
        return Service.of(createParam.getTitle(), createParam.getBasePrice(), createParam.getDescription(), category);
    }

    private ServiceRequest createServiceRequest(CustomerServiceRequestParam param, Customer customer, Service service) {
        return ServiceRequest.of(customer, service, param.getPrice(), param.getStartDate(), param.getDescription(), param.getAddress());
    }
}
