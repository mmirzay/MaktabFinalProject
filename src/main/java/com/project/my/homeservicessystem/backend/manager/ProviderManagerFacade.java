package com.project.my.homeservicessystem.backend.manager;

import com.project.my.homeservicessystem.backend.api.ProviderManagerInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.*;
import com.project.my.homeservicessystem.backend.api.dto.out.*;
import com.project.my.homeservicessystem.backend.entities.services.*;
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
public class ProviderManagerFacade implements ProviderManagerInterface {

    private final RoleService roleService;
    private final ProviderService providerService;
    private final ServiceService serviceService;
    private final ServiceRequestService requestService;
    private final ServiceOfferService offerService;
    private final UserFeedbackService feedbackService;

    @Override
    public ProviderRegisterResult registerProvider(ProviderRegisterParam param) {
        try {
            Role role = roleService.getRoleById(param.getRoleId());
            Provider toRegister = createProvider(param, role);
            Provider registered = providerService.addProvider(toRegister);
            return new ProviderRegisterResult(registered.getId());
        } catch (RoleException | ProviderException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ProviderProfileResult getProviderProfile(Long id) {
        try {
            Provider provider = providerService.getProviderById(id);
            return ProviderProfileResult.builder()
                    .firstName(provider.getFirstName())
                    .lastName(provider.getLastName())
                    .email(provider.getEmail())
                    .registeredDate(provider.getRegisterDate())
                    .credit(provider.getCredit())
                    .status(provider.getStatus())
                    .profilePhotoUrl(provider.getProfilePhotoUrl())
                    .build();
        } catch (ProviderException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ProviderUpdateResult updateProviderProfile(Long id, ProviderUpdateProfileParam param) {
        try {
            Provider provider = providerService.getProviderById(id);
            provider.setFirstName(param.getFirstName());
            provider.setLastName(param.getLastName());
            provider.setProfilePhotoUrl(param.getProfilePhotoUrl());
            String message = providerService.updateProvider(provider) ? "updated successfully." : "updating failed!";
            return new ProviderUpdateResult(id, message);
        } catch (ProviderException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ProviderUpdateResult updateProviderPassword(Long id, ProviderUpdatePasswordParam param) {
        try {
            Provider provider = providerService.getProviderById(id);
            if (provider.getPassword().equals(param.getOldPassword()) == false)
                throw new ProviderException("Current password is not correct.");
            if (Validator.validatePassword(param.getNewPassword()) == false)
                throw new ProviderException("New password is not valid.");
            provider.setPassword(param.getNewPassword());
            String message = providerService.updateProvider(provider) ? "updated successfully." : "updating failed!";
            return new ProviderUpdateResult(id, message);
        } catch (ProviderException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ProviderAddServiceResult addServiceForProvider(Long providerId, ProviderAddServiceParam param) {
        try {
            Provider provider = providerService.getProviderById(providerId);
            Service service = serviceService.getServiceById(param.getServiceId());
            if (provider.getServices().contains(service))
                throw new ProviderException("Service is added before.");
            provider.getServices().add(service);
            String message = providerService.updateProvider(provider) ? "added successfully." : "adding failed!";
            return new ProviderAddServiceResult(service.getId(), message);
        } catch (ServiceException | ProviderException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServicesList getProviderServices(Long providerId) {
        try {
            Provider provider = providerService.getProviderById(providerId);
            if (provider.getServices().isEmpty())
                throw new ProviderException("Services list is empty");
            return new ServicesList(List.copyOf(provider.getServices()));
        } catch (ProviderException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ProviderServiceOfferResult addProviderServiceOffer(Long providerId, ProviderServiceOfferParam param) {
        try {
            Provider provider = providerService.getProviderById(providerId);
            ServiceRequest request = requestService.getRequestById(param.getRequestId());
            if (provider.getServices().contains(request.getService()) == false)
                throw new ProviderException("Requested service is not provided");
            ServiceOffer toAdd = ServiceOffer.of(provider, request, param.getPrice(), param.getStartHour(), param.getDurationInHours());
            ServiceOffer added = offerService.addServiceOffer(toAdd);
            return new ProviderServiceOfferResult(added.getId());
        } catch (ProviderException | ServiceRequestException | ServiceOfferException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceOffersList getProviderOffers(Long providerId) {
        try {
            Provider provider = providerService.getProviderById(providerId);
            List<ServiceOffer> offers = offerService.getOffersOfProvider(provider);
            if (offers.isEmpty())
                throw new ProviderException("Offers list is empty");
            return new ServiceOffersList(offers);
        } catch (ProviderException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceOfferCancelResult cancelServiceOfferByProvider(Long providerId, Long offerId) {
        try {
            Provider provider = providerService.getProviderById(providerId);
            ServiceOffer offer = offerService.getOfferById(offerId);
            if (offer.getProvider().getId().equals(provider.getId()) == false)
                throw new ServiceOfferException("Offer is not belong to provider");
            if (offer.getStatus() != ServiceOfferStatus.UNDER_ACCEPTING)
                throw new ServiceOfferException("Offer can not be canceled due to its status");
            offer.setStatus(ServiceOfferStatus.CANCELED);
            String message = offerService.updateServiceOffer(offer) ? "Canceled successfully" : "Canceling failed";
            return new ServiceOfferCancelResult(offerId, message);
        } catch (ProviderException | ServiceOfferException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceOfferStartResult startServiceOfferByProvider(Long providerId, Long offerId) {
        try {
            Provider provider = providerService.getProviderById(providerId);
            ServiceOffer offer = offerService.getOfferById(offerId);
            if (offer.getProvider().getId().equals(provider.getId()) == false)
                throw new ServiceOfferException("Offer is not belong to provider");
            if (offer.getStatus() != ServiceOfferStatus.ACCEPTED)
                throw new ServiceOfferException("Offer can not be started due to its status");

            ServiceRequest request = offer.getRequest();
            request.setStatus(ServiceRequestStatus.STARTED);
            String message = requestService.updateServiceRequest(request) ? "Started successfully" : "Starting failed";
            return new ServiceOfferStartResult(offerId, message);
        } catch (ProviderException | ServiceOfferException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public ServiceOfferFinishResult finishServiceOfferByProvider(Long providerId, Long offerId) {
        try {
            Provider provider = providerService.getProviderById(providerId);
            ServiceOffer offer = offerService.getOfferById(offerId);
            if (offer.getProvider().getId().equals(provider.getId()) == false)
                throw new ServiceOfferException("Offer is not belong to provider");
            if (offer.getStatus() != ServiceOfferStatus.ACCEPTED)
                throw new ServiceOfferException("Offer can not be finished due to its status");

            ServiceRequest request = offer.getRequest();
            if (request.getStatus() != ServiceRequestStatus.STARTED)
                throw new ServiceOfferException("Offer of Request must be started before finishing");

            request.setStatus(ServiceRequestStatus.UNDER_CONFIRMING);
            String message = requestService.updateServiceRequest(request) ? "Finished successfully" : "Finishing failed";
            return new ServiceOfferFinishResult(offerId, message);
        } catch (ProviderException | ServiceOfferException e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public UserFeedBacksList getProviderFeedbacks(Long providerId) {
        try {
            Provider provider = providerService.getProviderById(providerId);
            List<UserFeedback> feedbacks = feedbackService.getAllFeedbacksOfProvider(provider);
            return new UserFeedBacksList(feedbacks);
        } catch (ProviderException e) {
            throw new ManagerException(e);
        }
    }

    private Provider createProvider(ProviderRegisterParam registerParam, Role role) {
        return Provider.of(registerParam.getEmail(),
                registerParam.getPassword(),
                role,
                registerParam.getFirstName(),
                registerParam.getLastName());
    }
}
