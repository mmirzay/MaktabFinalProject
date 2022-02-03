package com.project.my.homeservicessystem.backend.controllers;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.out.*;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final HomeServiceInterface manager;

    @GetMapping("/customer")
    public ResponseEntity<CustomersList> customersList() {
        try {
            CustomersList result = manager.getAllCustomers();
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @GetMapping("/provider")
    public ResponseEntity<ProvidersList> providersList() {
        try {
            ProvidersList result = manager.getAllProviders();
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @GetMapping("/request")
    public ResponseEntity<ServiceRequestsList> requestsList() {
        try {
            ServiceRequestsList result = manager.getAllServiceRequests();
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @GetMapping("/offer")
    public ResponseEntity<ServiceOffersList> offersList() {
        try {
            ServiceOffersList result = manager.getAllServiceOffers();
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @GetMapping("/feedback")
    public ResponseEntity<UserFeedBacksList> feedbacksList() {
        try {
            UserFeedBacksList result = manager.getAllUserFeedbacks();
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<CustomerDeleteResult> removeCustomer(@PathVariable Long id) {
        try {
            CustomerDeleteResult result = manager.deleteCustomerById(id);
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/provider/{id}")
    public ResponseEntity<ProviderDeleteResult> removeProvider(@PathVariable Long id) {
        try {
            ProviderDeleteResult result = manager.deleteProviderById(id);
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/request/{id}")
    public ResponseEntity<ServiceRequestDeleteResult> removeRequest(@PathVariable Long id) {
        try {
            ServiceRequestDeleteResult result = manager.deleteServiceRequestById(id);
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/offer/{id}")
    public ResponseEntity<ServiceOfferDeleteResult> removeOffer(@PathVariable Long id) {
        try {
            ServiceOfferDeleteResult result = manager.deleteServiceOfferById(id);
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<UserFeedbackDeleteResult> removeFeedback(@PathVariable Long id) {
        try {
            UserFeedbackDeleteResult result = manager.deleteUserFeedbackById(id);
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }
}
