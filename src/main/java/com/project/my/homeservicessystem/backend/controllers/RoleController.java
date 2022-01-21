package com.project.my.homeservicessystem.backend.controllers;

import com.project.my.homeservicessystem.backend.api.HomeServiceInterface;
import com.project.my.homeservicessystem.backend.api.dto.in.RoleCreateParam;
import com.project.my.homeservicessystem.backend.api.dto.out.RoleCreateResult;
import com.project.my.homeservicessystem.backend.api.dto.out.RoleDeleteResult;
import com.project.my.homeservicessystem.backend.api.dto.out.RolesList;
import com.project.my.homeservicessystem.backend.exceptions.ManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final HomeServiceInterface manager;

    @PostMapping
    public ResponseEntity<RoleCreateResult> create(@RequestBody RoleCreateParam createParam) {
        try {
            RoleCreateResult result = manager.addRole(createParam);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping
    public ResponseEntity<RolesList> rolesList() {
        try {
            RolesList result = manager.getRolesList();
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RoleDeleteResult> remove(@PathVariable Long id) {
        try {
            RoleDeleteResult result = manager.deleteRoleById(id);
            return ResponseEntity.ok().body(result);
        } catch (ManagerException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

}
