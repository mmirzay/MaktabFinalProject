package com.project.my.homeservicessystem.backend.manager;

import com.project.my.homeservicessystem.backend.api.CustomerManagerInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.*;
import com.project.my.homeservicessystem.backend.api.dto.out.*;
import com.project.my.homeservicessystem.backend.entities.services.*;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Provider;
import com.project.my.homeservicessystem.backend.entities.users.Role;
import com.project.my.homeservicessystem.backend.entities.users.UserFeedback;
import com.project.my.homeservicessystem.backend.exceptions.*;
import com.project.my.homeservicessystem.backend.services.*;
import com.project.my.homeservicessystem.backend.utilities.Validator;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CustomerManagerFacade implements CustomerManagerInterface {

    private final RoleService roleService;
    private final CustomerService customerService;
    private final ProviderService providerService;
    private final ServiceService serviceService;
    private final ServiceRequestService requestService;
    private final ServiceOfferService offerService;
    private final UserFeedbackService feedbackService;

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
    public ServiceRequestsList getCustomerRequests(Long customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
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
            for (ServiceOffer offer : offerService.getOffersOfServiceRequest(request))
                if (offer.getStatus() == ServiceOfferStatus.ACCEPTED) {
                    offer.setStatus(ServiceOfferStatus.REJECTED);
                    offerService.updateServiceOffer(offer);
                    break;
                }
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
            if (offer.getStatus() != ServiceOfferStatus.UNDER_ACCEPTING)
                throw new ServiceOfferException("Offer can not be accepted due to its status");
            offer.setStatus(ServiceOfferStatus.ACCEPTED);

            request.setStatus(ServiceRequestStatus.ON_GOING);
            offerService.updateServiceOffer(offer);
            String message = requestService.updateServiceRequest(request) ? "Accepted successfully" : "Accepting failed";
            return new ServiceOfferAcceptResult(offerId, message);
        } catch (CustomerException | ServiceRequestException | ServiceOfferException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceOfferConfirmResult confirmServiceOfferByCustomer(Long customerId, Long reqId, Long offerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);

            ServiceRequest request = requestService.getRequestById(reqId);
            if (request.getCustomer().getId().equals(customer.getId()) == false)
                throw new ServiceRequestException("Request is not belong to customer");
            if (request.getStatus() != ServiceRequestStatus.UNDER_CONFIRMING)
                throw new ServiceRequestException("Request can not be confirmed due to its status");

            ServiceOffer offer = offerService.getOfferById(offerId);
            if (offer.getRequest().getId().equals(request.getId()) == false)
                throw new ServiceOfferException("Offer is not belong to request");
            if (offer.getStatus() != ServiceOfferStatus.ACCEPTED)
                throw new ServiceOfferException("Offer can not be confirmed due to its status");

            request.setStatus(ServiceRequestStatus.DONE);
            String message = requestService.updateServiceRequest(request) ? "Confirmed successfully" : "Confirming failed";
            return new ServiceOfferConfirmResult(offerId, message);
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
            if (offer.getStatus() != ServiceOfferStatus.ACCEPTED)
                throw new ServiceOfferException("Offer can not be paid due to its status");

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

    @Override
    public CustomerFeedbackResult addCustomerFeedback(Long customerId, CustomerFeedbackParam param) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            Provider provider = providerService.getProviderById(param.getProviderId());
            Service service = serviceService.getServiceById(param.getServiceId());
            UserFeedback toAdd = UserFeedback.of(customer, provider, service, param.getRate(), param.getText());
            UserFeedback added = feedbackService.addUserFeedback(toAdd);
            provider.setScore(provider.getScore() + added.getRate());
            String message = providerService.updateProvider(provider) ? "Added successfully" : "Adding failed";
            return new CustomerFeedbackResult(added.getId(), message);
        } catch (CustomerException | ProviderException | ServiceException | UserFeedBackException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public UserFeedBacksList getCustomerFeedbacks(Long customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            List<UserFeedback> feedbacks = feedbackService.getAllFeedbacksOfCustomer(customer);
            if (feedbacks.isEmpty())
                throw new UserFeedBackException("Feedback list is empty");
            return new UserFeedBacksList(feedbacks);
        } catch (CustomerException | UserFeedBackException e) {
            throw new ManagerException(e);
        }
    }

    private Customer createCustomer(CustomerRegisterParam registerParam, Role role) {
        return Customer.of(registerParam.getEmail(),
                registerParam.getPassword(),
                role,
                registerParam.getFirstName(),
                registerParam.getLastName());
    }

    private ServiceRequest createServiceRequest(CustomerServiceRequestParam param, Customer customer, Service service) {
        return ServiceRequest.of(customer, service, param.getPrice(), param.getStartDate(), param.getDescription(), param.getAddress());
    }
}
