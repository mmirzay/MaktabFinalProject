package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.out.*;

public interface HomeServiceInterface {

    CustomersList getAllCustomers();

    ProvidersList getAllProviders();

    ServiceRequestsList getAllServiceRequests();

    ServiceOffersList getAllServiceOffers();

    UserFeedBacksList getAllUserFeedbacks();

    CustomerDeleteResult deleteCustomerById(Long id);

    ProviderDeleteResult deleteProviderById(Long id);

    ServiceRequestDeleteResult deleteServiceRequestById(Long id);

    ServiceOfferDeleteResult deleteServiceOfferById(Long id);

    UserFeedbackDeleteResult deleteUserFeedbackById(Long id);
}
