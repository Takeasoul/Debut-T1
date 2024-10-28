package org.example.debut.services.impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.debut.config.AccessDeniedException;
import org.example.debut.entity.Card;
import org.example.debut.entity.CardMember;
import org.example.debut.entity.RoleType;
import org.example.debut.entity.TemplateType;
import org.example.debut.entity.mapper.CardMapper;
import org.example.debut.entity.mapper.RoleMapper;
import org.example.debut.repositories.CardMemberRepository;
import org.example.debut.repositories.CardRepository;
import org.example.debut.repositories.RoleRepository;
import org.example.debut.repositories.TemplateTypeRepository;
import org.example.debut.services.CardService;
import org.example.debut.services.RoleService;
import org.example.debut.web.DTO.request.CardRequest;
import org.example.debut.web.DTO.request.RoleRequest;
import org.example.debut.web.DTO.response.CardResponse;
import org.example.debut.web.DTO.response.ModelListResponse;
import org.example.debut.web.DTO.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final RoleRepository repository;

    private final CardMemberRepository cardMemberRepository;

    @Override
    @Transactional
    @Async
    public CompletableFuture<ModelListResponse<RoleResponse>> findAll(Pageable pageable) {
        log.info("Find all roles");
        Page<RoleType> roleTypes = repository.findAll(pageable);
        return CompletableFuture.completedFuture(ModelListResponse.<RoleResponse>builder()
                .totalCount(roleTypes.getTotalElements())
                .data(roleTypes.stream().map(roleMapper::roleToResponse).toList())
                .build());

    }

    @Override
    @Transactional
    public RoleResponse findById(UUID id) {
        log.info("Find role with ID: {}", id);
        return roleMapper.roleToResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Role with ID {0} not found!", id)
                )));
    }


    @Override
    @Transactional
    public RoleResponse create(RoleRequest entityRequest) {
        log.info("Create role {}", entityRequest);
            RoleType roleType = roleMapper.requestToRole(entityRequest);
            return roleMapper.roleToResponse(repository.save(roleType));
    }

    @Override
    @Transactional
    public RoleResponse update(UUID uuid, RoleRequest entityRequest) {
            RoleType roleType = roleMapper.requestToRole(entityRequest);
            roleType.setId(uuid);
            repository.save(roleType);
            return roleMapper.roleToResponse(roleType);
    }



    @Override
    @Transactional
    public void deleteById(UUID id) {
        log.info("Delete card with ID: {}", id);
        repository.deleteById(id);
    }
}
