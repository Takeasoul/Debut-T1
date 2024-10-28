package org.example.debut.entity.mapper;

import org.example.debut.entity.Card;
import org.example.debut.entity.RoleType;
import org.example.debut.web.DTO.request.CardRequest;
import org.example.debut.web.DTO.request.RoleRequest;
import org.example.debut.web.DTO.response.CardResponse;
import org.example.debut.web.DTO.response.RoleResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(RoleMapperDelegate.class)
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoleMapper {
    RoleType requestToRole(RoleRequest request);

    RoleResponse roleToResponse(RoleType roleType);
}
