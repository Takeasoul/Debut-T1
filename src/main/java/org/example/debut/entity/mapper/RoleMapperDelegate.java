package org.example.debut.entity.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.example.debut.entity.CardMember;
import org.example.debut.entity.RoleType;
import org.example.debut.repositories.CardRepository;
import org.example.debut.repositories.RoleRepository;
import org.example.debut.web.DTO.request.CardMemberRequest;
import org.example.debut.web.DTO.request.RoleRequest;
import org.example.debut.web.DTO.response.CardMemberResponse;
import org.example.debut.web.DTO.response.RoleResponse;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RoleMapperDelegate implements RoleMapper {

    @Autowired
    private RoleMapper delegate;


    @Autowired
    private RoleRepository roleRepository;


    @Override
    public RoleType requestToRole(RoleRequest request) {
        RoleType roleType = new RoleType();
        roleType.setRole(request.getRole());
        return roleType;
    }

    @Override
    public RoleResponse roleToResponse(RoleType roleType) {
        RoleResponse response = new RoleResponse();
        response.setRoleId(roleType.getId());
        response.setRole(roleType.getRole());
        return response;
    }

}