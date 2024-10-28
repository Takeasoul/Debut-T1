package org.example.debut.web.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.example.debut.config.AccessDeniedException;
import org.example.debut.services.RoleService;
import org.example.debut.services.TemplateService;
import org.example.debut.web.DTO.request.RoleRequest;
import org.example.debut.web.DTO.request.TemplateRequest;
import org.example.debut.web.DTO.response.ErrorResponse;
import org.example.debut.web.DTO.response.ModelListResponse;
import org.example.debut.web.DTO.response.RoleResponse;
import org.example.debut.web.DTO.response.TemplateResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Template", description = "Шаблоны")
@RequestMapping(path= "api/v1/template",produces = "application/json")
@CrossOrigin()
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                templateService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<ModelListResponse<TemplateResponse>> getAllTemplates(Pageable pageable) {
        try {
            return ResponseEntity.ok(templateService.findAll(pageable).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateTemplate(@PathVariable UUID id, @RequestBody TemplateRequest request) {
        return ResponseEntity.ok(templateService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable UUID id) {
        templateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> createTemplate(@RequestBody TemplateRequest request) {
        TemplateResponse response = templateService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/byRole/{id}")
    public ResponseEntity<ModelListResponse<TemplateResponse>> getAllTemplatesByRoleId(@PathVariable UUID id, Pageable pageable) {
        try {
            return ResponseEntity.ok(templateService.findAllByRoleId(id, pageable).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}