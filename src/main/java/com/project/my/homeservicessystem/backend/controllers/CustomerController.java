package com.project.my.homeservicessystem.backend.controllers;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerRegisterParam;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerServiceRequestParam;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerUpdatePasswordParam;
import com.project.my.homeservicessystem.backend.api.dto.in.CustomerUpdateProfileParam;
import com.project.my.homeservicessystem.backend.api.dto.out.*;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final HomeServiceInterface manager;

    @PostMapping("/register")
    public ResponseEntity<CustomerRegisterResult> register(@RequestBody CustomerRegisterParam registerParam) {
        try {
            CustomerRegisterResult result = manager.registerCustomer(registerParam);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerProfileResult> getProfile(@PathVariable Long id) {
        try {
            CustomerProfileResult result = manager.getCustomerProfile(id);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerUpdateResult> updateProfile(@PathVariable Long id, @RequestBody CustomerUpdateProfileParam param) {
        try {
            CustomerUpdateResult result = manager.updateCustomerProfile(id, param);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<CustomerUpdateResult> updatePassword(@PathVariable Long id, @RequestBody CustomerUpdatePasswordParam param) {
        try {
            CustomerUpdateResult result = manager.updateCustomerPassword(id, param);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PostMapping("/{customerId}/request")
    public ResponseEntity<CustomerServiceRequestResult> requestService(@PathVariable Long customerId, @RequestBody CustomerServiceRequestParam param) {
        try {
            CustomerServiceRequestResult result = manager.requestServiceByCustomer(customerId, param);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/{customerId}/request")
    public ResponseEntity<ServiceRequestsList> requestsList(@PathVariable Long customerId) {
        try {
            ServiceRequestsList result = manager.getCustomerRequests(customerId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{customerId}/request/{reqId}/cancel")
    public ResponseEntity<ServiceRequestCancelResult> cancelRequest(@PathVariable Long customerId, @PathVariable Long reqId) {
        try {
            ServiceRequestCancelResult result = manager.cancelServiceRequestByCustomer(customerId, reqId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/{customerId}/request/{reqId}/offer")
    public ResponseEntity<ServiceOffersList> offersListOfCustomerRequest(@PathVariable Long customerId, @PathVariable Long reqId) {
        try {
            ServiceOffersList result = manager.getOffersOfCustomerRequest(customerId, reqId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{customerId}/request/{reqId}/offer/{offerId}/accept")
    public ResponseEntity<ServiceOfferAcceptResult> acceptOfferByCustomer(@PathVariable Long customerId, @PathVariable Long reqId, @PathVariable Long offerId) {
        try {
            ServiceOfferAcceptResult result = manager.acceptServiceOfferByCustomer(customerId, reqId, offerId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{customerId}/request/{reqId}/offer/{offerId}/confirm")
    public ResponseEntity<ServiceOfferConfirmResult> confirmOfferByCustomer(@PathVariable Long customerId, @PathVariable Long reqId, @PathVariable Long offerId) {
        try {
            ServiceOfferConfirmResult result = manager.confirmServiceOfferByCustomer(customerId, reqId, offerId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PutMapping("/{customerId}/request/{reqId}/offer/{offerId}/pay")
    public ResponseEntity<ServiceOfferPayResult> payOfferByCustomer(@PathVariable Long customerId, @PathVariable Long reqId, @PathVariable Long offerId) {
        try {
            ServiceOfferPayResult result = manager.payServiceOfferByCustomer(customerId, reqId, offerId);
            return ResponseEntity.ok(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }
}
