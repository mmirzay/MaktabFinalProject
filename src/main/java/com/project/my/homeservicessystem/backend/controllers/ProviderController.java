package com.project.my.homeservicessystem.backend.controllers;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.*;
import com.project.my.homeservicessystem.backend.api.dto.out.*;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/provider")
public class ProviderController {
    private final HomeServiceInterface manager;

    @PostMapping("/register")
    public ResponseEntity<ProviderRegisterResult> register(@RequestBody ProviderRegisterParam param) {
        try {
            ProviderRegisterResult result = manager.registerProvider(param);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderProfileResult> getProfile(@PathVariable Long id) {
        try {
            ProviderProfileResult result = manager.getProviderProfile(id);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderUpdateResult> updateProfile(@PathVariable Long id, @RequestBody ProviderUpdateProfileParam param) {
        try {
            ProviderUpdateResult result = manager.updateProviderProfile(id, param);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<ProviderUpdateResult> updatePassword(@PathVariable Long id, @RequestBody ProviderUpdatePasswordParam param) {
        try {
            ProviderUpdateResult result = manager.updateProviderPassword(id, param);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PostMapping("/{providerId}/service")
    public ResponseEntity<ProviderAddServiceResult> addService(@PathVariable Long providerId, @RequestBody ProviderAddServiceParam param) {
        try {
            ProviderAddServiceResult result = manager.addServiceForProvider(providerId, param);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/{providerId}/service")
    public ResponseEntity<ServicesList> servicesList(@PathVariable Long providerId) {
        try {
            ServicesList result = manager.getProviderServices(providerId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @PostMapping("/{providerId}/offer")
    public ResponseEntity<ProviderServiceOfferResult> offerToServiceRequest(@PathVariable Long providerId, @RequestBody ProviderServiceOfferParam param) {
        try {
            ProviderServiceOfferResult result = manager.addProviderServiceOffer(providerId, param);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/{providerId}/offer")
    public ResponseEntity<ServiceOffersList> offersList(@PathVariable Long providerId) {
        try {
            ServiceOffersList result = manager.getProviderOffers(providerId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{providerId}/offer/{offerId}/cancel")
    public ResponseEntity<ServiceOfferCancelResult> cancelOffer(@PathVariable Long providerId, @PathVariable Long offerId) {
        try {
            ServiceOfferCancelResult result = manager.cancelServiceOfferByProvider(providerId, offerId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{providerId}/offer/{offerId}/start")
    public ResponseEntity<ServiceOfferStartResult> startOffer(@PathVariable Long providerId, @PathVariable Long offerId) {
        try {
            ServiceOfferStartResult result = manager.startServiceOfferByProvider(providerId, offerId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{providerId}/offer/{offerId}/finish")
    public ResponseEntity<ServiceOfferFinishResult> finishOffer(@PathVariable Long providerId, @PathVariable Long offerId) {
        try {
            ServiceOfferFinishResult result = manager.finishServiceOfferByProvider(providerId, offerId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/{providerId}/feedback")
    public ResponseEntity<UserFeedBacksList> feedbacksList(@PathVariable Long providerId) {
        try {
            UserFeedBacksList result = manager.getProviderFeedbacks(providerId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }
}
