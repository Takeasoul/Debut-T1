package org.example.debut.services;


import org.example.debut.web.DTO.request.RoleRequest;
import org.example.debut.web.DTO.request.TemplateRequest;
import org.example.debut.web.DTO.response.ModelListResponse;
import org.example.debut.web.DTO.response.RoleResponse;
import org.example.debut.web.DTO.response.TemplateResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface TemplateService extends EntityService<TemplateResponse, TemplateRequest, UUID> {
    CompletableFuture<ModelListResponse<TemplateResponse>> findAllByRoleId(UUID roleId, Pageable pageable);
}