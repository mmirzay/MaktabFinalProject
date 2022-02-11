package com.project.my.homeservicessystem.backend.api;

import com.project.my.homeservicessystem.backend.api.dto.in.*;
import com.project.my.homeservicessystem.backend.api.dto.out.*;

public interface ProviderManagerInterface {

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

    UserFeedBacksList getProviderFeedbacks(Long providerId);
}
