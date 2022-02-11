package com.project.my.homeservicessystem.backend.manager;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.out.*;
import com.project.my.homeservicessystem.backend.entities.services.ServiceOffer;
import com.project.my.homeservicessystem.backend.entities.services.ServiceRequest;
import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.Provider;
import com.project.my.homeservicessystem.backend.entities.users.UserFeedback;
import com.project.my.homeservicessystem.backend.exceptions.*;
import com.project.my.homeservicessystem.backend.services.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class HomeServiceManager implements HomeServiceInterface {
    private final CustomerService customerService;
    private final ProviderService providerService;
    private final ServiceRequestService requestService;
    private final ServiceOfferService offerService;
    private final UserFeedbackService feedbackService;

    @Override
    public CustomersList getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            if (customers.isEmpty())
                throw new CustomerException("Customers list is empty");
            return new CustomersList(customers);
        } catch (CustomerException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ProvidersList getAllProviders() {
        try {
            List<Provider> providers = providerService.getAllProviders();
            if (providers.isEmpty())
                throw new ProviderException("Provider list is empty");
            return new ProvidersList(providers);
        } catch (ProviderException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceRequestsList getAllServiceRequests() {
        try {
            List<ServiceRequest> requests = requestService.getAllServiceRequests();
            if (requests.isEmpty())
                throw new ServiceRequestException("Service requests list is empty");
            return new ServiceRequestsList(requests);
        } catch (ServiceRequestException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceOffersList getAllServiceOffers() {
        try {
            List<ServiceOffer> offers = offerService.getAllServiceOffers();
            if (offers.isEmpty())
                throw new ServiceOfferException("Service offers list is empty");
            return new ServiceOffersList(offers);
        } catch (ServiceOfferException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public UserFeedBacksList getAllUserFeedbacks() {
        try {
            List<UserFeedback> feedbacks = feedbackService.getAllFeedbacks();
            if (feedbacks.isEmpty())
                throw new UserFeedBackException("User feedbacks list is empty");
            return new UserFeedBacksList(feedbacks);
        } catch (UserFeedBackException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public CustomerDeleteResult deleteCustomerById(Long id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            customerService.deleteCustomerById(customer.getId());
            return new CustomerDeleteResult(id, customer.getEmail());
        } catch (CustomerException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ProviderDeleteResult deleteProviderById(Long id) {
        try {
            Provider provider = providerService.getProviderById(id);
            providerService.deleteProviderById(provider.getId());
            return new ProviderDeleteResult(id, provider.getEmail());
        } catch (ProviderException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceRequestDeleteResult deleteServiceRequestById(Long id) {
        try {
            ServiceRequest request = requestService.getRequestById(id);
            requestService.deleteServiceRequestById(request.getId());
            return new ServiceRequestDeleteResult(id, request.getCustomer().getEmail());
        } catch (ServiceRequestException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceOfferDeleteResult deleteServiceOfferById(Long id) {
        try {
            ServiceOffer offer = offerService.getOfferById(id);
            offerService.deleteServiceOfferById(offer.getId());
            return new ServiceOfferDeleteResult(id, offer.getProvider().getEmail());
        } catch (ServiceOfferException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public UserFeedbackDeleteResult deleteUserFeedbackById(Long id) {
        try {
            UserFeedback feedback = feedbackService.getFeedbackById(id);
            feedbackService.deleteUserFeedbackById(feedback.getId());
            return new UserFeedbackDeleteResult(id, feedback.getCustomer().getEmail(), feedback.getProvider().getEmail());
        } catch (UserFeedBackException e) {
            throw new ManagerException(e);
        }
    }
}
