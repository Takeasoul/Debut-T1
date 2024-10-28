package org.example.debut.entity.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.example.debut.entity.RoleType;
import org.example.debut.entity.TemplateType;
import org.example.debut.repositories.RoleRepository;
import org.example.debut.repositories.TemplateTypeRepository;
import org.example.debut.web.DTO.request.RoleRequest;
import org.example.debut.web.DTO.request.TemplateRequest;
import org.example.debut.web.DTO.response.RoleResponse;
import org.example.debut.web.DTO.response.TemplateResponse;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;

public abstract class TemplateMapperDelegate implements TemplateMapper {

    @Autowired
    private TemplateMapper delegate;


    @Autowired
    private TemplateTypeRepository templateTypeRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public TemplateType requestToTemplate(TemplateRequest request) {
        TemplateType templateType = delegate.requestToTemplate(request);
        RoleType roleType = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found for id: " + request.getRoleId()));
        templateType.setRoleType(roleType);
        return templateType;
    }

    @Override
    public TemplateResponse templateToResponse(TemplateType templateType) {
        TemplateResponse templateResponse = delegate.templateToResponse(templateType);
        templateResponse.setRole(templateType.getRoleType().getRole());
        System.out.println(templateResponse.getRole());
        return templateResponse;
    }

}