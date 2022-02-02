package com.project.my.homeservicessystem.backend.controllers;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.ProviderRegisterParam;
import com.project.my.homeservicessystem.backend.api.dto.in.ProviderUpdatePasswordParam;
import com.project.my.homeservicessystem.backend.api.dto.in.ProviderUpdateProfileParam;
import com.project.my.homeservicessystem.backend.api.dto.out.ProviderProfileResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ProviderRegisterResult;
import com.project.my.homeservicessystem.backend.api.dto.out.ProviderUpdateResult;
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
}
