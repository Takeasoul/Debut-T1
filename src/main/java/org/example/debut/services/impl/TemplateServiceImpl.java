package org.example.debut.services.impl;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.debut.entity.Card;
import org.example.debut.entity.RoleType;
import org.example.debut.entity.TemplateType;
import org.example.debut.entity.mapper.RoleMapper;
import org.example.debut.entity.mapper.TemplateMapper;
import org.example.debut.repositories.CardMemberRepository;
import org.example.debut.repositories.CardRepository;
import org.example.debut.repositories.RoleRepository;
import org.example.debut.repositories.TemplateTypeRepository;
import org.example.debut.services.RoleService;
import org.example.debut.services.TemplateService;
import org.example.debut.web.DTO.request.RoleRequest;
import org.example.debut.web.DTO.request.TemplateRequest;
import org.example.debut.web.DTO.response.ModelListResponse;
import org.example.debut.web.DTO.response.RoleResponse;
import org.example.debut.web.DTO.response.TemplateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateMapper templateMapper;

    private final TemplateTypeRepository repository;
    private final TemplateTypeRepository templateTypeRepository;
    private final CardRepository cardRepository;
    private final CardServiceImpl cardServiceImpl;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    @Async
    public CompletableFuture<ModelListResponse<TemplateResponse>> findAll(Pageable pageable) {
        log.info("Find all templates");
        Page<TemplateType> templateTypes = repository.findAll(pageable);
        return CompletableFuture.completedFuture(ModelListResponse.<TemplateResponse>builder()
                .totalCount(templateTypes.getTotalElements())
                .data(templateTypes.stream().map(templateMapper::templateToResponse).toList())
                .build());

    }

    @Override
    @Transactional
    public TemplateResponse findById(UUID id) {
        log.info("Find template with ID: {}", id);
        return templateMapper.templateToResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Template with ID {0} not found!", id)
                )));
    }


    @Override
    @Transactional
    public TemplateResponse create(TemplateRequest entityRequest) {
        log.info("Create template {}", entityRequest);
            TemplateType templateType = templateMapper.requestToTemplate(entityRequest);
        if (templateTypeRepository.findByName(templateType.getName()).isEmpty())  {
                return templateMapper.templateToResponse(repository.save(templateType));
            }
            else throw new EntityExistsException(MessageFormat.format("Template with name {0} already exists!", templateType.getName()));
    }

    @Override
    @Transactional
    public TemplateResponse update(UUID uuid, TemplateRequest entityRequest) {
            TemplateType templateType = templateMapper.requestToTemplate(entityRequest);
        if (templateTypeRepository.findByName(templateType.getName()).isEmpty())  {
            templateType.setId(uuid);
            repository.save(templateType);
            return templateMapper.templateToResponse(templateType);}
        else throw new EntityExistsException(MessageFormat.format("Template with name {0} already exists!", templateType.getName()));
    }



    @Override
    @Transactional
    public void deleteById(UUID id) {
        log.info("Delete template with ID: {}", id);

        List<Card> cards = cardRepository.findAllByTemplateType(repository.findById(id).orElseThrow(EntityNotFoundException::new));
        cards.forEach(card -> card.setTemplateType(null));
        repository.deleteById(id);
    }


    @Override
    public CompletableFuture<ModelListResponse<TemplateResponse>> findAllByRoleId(UUID roleId, Pageable pageable) {
        log.info("Find all templates by role");
        RoleType roleType = roleRepository.findById(roleId).orElseThrow(EntityNotFoundException::new);
        Page<TemplateType> templateTypes = repository.findAllByRoleType(roleType, pageable);
        return CompletableFuture.completedFuture(ModelListResponse.<TemplateResponse>builder()
                .totalCount(templateTypes.getTotalElements())
                .data(templateTypes.stream().map(templateMapper::templateToResponse).toList())
                .build());

    }
}
