package org.example.debut.entity.mapper;

import org.example.debut.entity.RoleType;
import org.example.debut.entity.TemplateType;
import org.example.debut.web.DTO.request.RoleRequest;
import org.example.debut.web.DTO.request.TemplateRequest;
import org.example.debut.web.DTO.response.RoleResponse;
import org.example.debut.web.DTO.response.TemplateResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(TemplateMapperDelegate.class)
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TemplateMapper {
    TemplateType requestToTemplate(TemplateRequest request);

    TemplateResponse templateToResponse(TemplateType templateType);
}