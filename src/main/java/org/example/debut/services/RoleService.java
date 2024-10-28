package org.example.debut.services;


import org.example.debut.web.DTO.request.RoleRequest;
import org.example.debut.web.DTO.response.RoleResponse;

import java.util.UUID;

public interface RoleService extends EntityService<RoleResponse, RoleRequest, UUID> {
}