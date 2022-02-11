package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.in.*;
import com.project.my.homeservicessystem.backend.api.dto.out.*;

public interface CustomerManagerInterface {

    CustomerRegisterResult registerCustomer(CustomerRegisterParam registerParam);

    CustomerProfileResult getCustomerProfile(Long id);

    CustomerUpdateResult updateCustomerProfile(Long id, CustomerUpdateProfileParam param);

    CustomerUpdateResult updateCustomerPassword(Long id, CustomerUpdatePasswordParam param);

    //Service Requests
    CustomerServiceRequestResult requestServiceByCustomer(Long id, CustomerServiceRequestParam param);

    ServiceRequestsList getCustomerRequests(Long customerId);

    ServiceRequestCancelResult cancelServiceRequestByCustomer(Long customerId, Long reqId);


    //Service offers
    ServiceOffersList getOffersOfCustomerRequest(Long customerId, Long reqId);

    ServiceOfferAcceptResult acceptServiceOfferByCustomer(Long customerId, Long reqId, Long offerId);

    ServiceOfferConfirmResult confirmServiceOfferByCustomer(Long customerId, Long reqId, Long offerId);

    ServiceOfferPayResult payServiceOfferByCustomer(Long customerId, Long reqId, Long offerId);

    //user feedbacks
    CustomerFeedbackResult addCustomerFeedback(Long customerId, CustomerFeedbackParam param);

    UserFeedBacksList getCustomerFeedbacks(Long customerId);
}
