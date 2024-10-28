package org.example.debut.web.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.debut.config.AccessDeniedException;
import org.example.debut.services.CardMembersService;
import org.example.debut.services.RoleService;
import org.example.debut.web.DTO.request.CardRequest;
import org.example.debut.web.DTO.request.RoleRequest;
import org.example.debut.web.DTO.response.CardResponse;
import org.example.debut.web.DTO.response.ErrorResponse;
import org.example.debut.web.DTO.response.ModelListResponse;
import org.example.debut.web.DTO.response.RoleResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Role", description = "Роли")
@RequestMapping(path= "api/v1/role",produces = "application/json")
@CrossOrigin()
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                roleService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<ModelListResponse<RoleResponse>> getAllRoles(Pageable pageable) {
        try {
            return ResponseEntity.ok(roleService.findAll(pageable).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }}

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable UUID id, @RequestBody RoleRequest request) {

            return ResponseEntity.ok(roleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleRequest request) {
            RoleResponse response = roleService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
