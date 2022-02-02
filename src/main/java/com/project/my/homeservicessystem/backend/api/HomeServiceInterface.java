package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.in.*;
import com.project.my.homeservicessystem.backend.api.dto.out.*;

public interface HomeServiceInterface {

    // Roles
    RoleCreateResult addRole(RoleCreateParam createParam);

    RolesList getRolesList();

    RoleDeleteResult deleteRoleById(Long id);


    //Customers
    CustomerRegisterResult registerCustomer(CustomerRegisterParam registerParam);

    CustomerProfileResult getCustomerProfile(Long id);

    CustomerUpdateResult updateCustomerProfile(Long id, CustomerUpdateProfileParam param);

    CustomerUpdateResult updateCustomerPassword(Long id, CustomerUpdatePasswordParam param);

    //Providers
    ProviderRegisterResult registerProvider(ProviderRegisterParam param);

    ProviderProfileResult getProviderProfile(Long id);

    ProviderUpdateResult updateProviderProfile(Long id, ProviderUpdateProfileParam param);

    ProviderUpdateResult updateProviderPassword(Long id, ProviderUpdatePasswordParam param);

    ProviderAddServiceResult addServiceForProvider(Long providerId, ProviderAddServiceParam param);

    ServicesList getProviderServices(Long providerId);

    ProviderServiceOfferResult addProviderServiceOffer(Long providerId, ProviderServiceOfferParam param);

    ServiceOffersList getProviderOffers(Long providerId);

    ServiceOfferCancelResult cancelServiceOfferByProvider(Long providerId, Long offerId);

    ServiceOfferStartResult startServiceOfferByProvider(Long providerId, Long offerId);

    ServiceOfferFinishResult finishServiceOfferByProvider(Long providerId, Long offerId);


    //ServiceCategories
    ServiceCategoryCreateResult addServiceCategory(ServiceCategoryCreateParam createParam);

    ServiceCategoriesList getServiceCategoriesList();

    ServiceCategoryUpdateResult updateServiceCategory(Long id, ServiceCategoryUpdateParam param);

    ServiceCategoryDeleteResult deleteServiceCategoryById(Long id);


    //Services
    ServiceCreateResult addService(ServiceCreateParam createParam);

    ServicesList getServicesList();

    ServiceUpdateResult updateService(Long id, ServiceUpdateParam param);

    ServiceDeleteResult deleteServiceById(Long id);



    //Service Requests
    CustomerServiceRequestResult requestServiceByCustomer(Long id, CustomerServiceRequestParam param);

    ServiceRequestsList getCustomerRequests(Long customerId);

    ServiceRequestCancelResult cancelServiceRequestByCustomer(Long customerId, Long reqId);


    //Service offers
    ServiceOffersList getOffersOfCustomerRequest(Long customerId, Long reqId);

    ServiceOfferAcceptResult acceptServiceOfferByCustomer(Long customerId, Long reqId, Long offerId);

    ServiceOfferPayResult payServiceOfferByCustomer(Long customerId, Long reqId, Long offerId);
}
