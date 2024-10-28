package org.example.debut.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.debut.entity.CardMember;
import org.example.debut.entity.TemplateType;
import org.example.debut.entity.mapper.CardMemberMapper;
import org.example.debut.repositories.CardMemberRepository;
import org.example.debut.repositories.TemplateTypeRepository;
import org.example.debut.services.CardMembersService;
import org.example.debut.web.DTO.request.CardMemberRequest;
import org.example.debut.web.DTO.response.CardMemberResponse;
import org.example.debut.web.DTO.response.ModelListResponse;
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
public class CardMemberServiceImpl implements CardMembersService {

    private final CardMemberMapper cardMemberMapper;
    private final CardMemberRepository repository;

    @Override
    @Async
    public CompletableFuture<ModelListResponse<CardMemberResponse>> findAll(Pageable pageable) {
        log.info("Find all card members");
        Page<CardMember> cardMembers = repository.findAll(pageable);
        return CompletableFuture.completedFuture(ModelListResponse.<CardMemberResponse>builder()
                .totalCount(cardMembers.getTotalElements())
                .data(cardMembers.stream().map(cardMemberMapper::cardMemberToResponse).toList())
                .build());
    }

    @Override
    @Transactional
    public CardMemberResponse findById(UUID id) {
        log.info("Find card member with ID: {}", id);
        return cardMemberMapper.cardMemberToResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Card member with ID {0} not found!", id)
                )));
    }

    @Override
    public CardMemberResponse create(CardMemberRequest entityRequest) {
        log.info("Create card member {}",entityRequest);
        return cardMemberMapper.cardMemberToResponse(
                repository.save(cardMemberMapper.requestToCardMember(entityRequest))
        );
    }


    @Override
    public void deleteById(UUID id) {
        log.info("Delete card member with ID: {}", id);
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public CardMemberResponse update(UUID uuid, CardMemberRequest entityRequest) {
        CardMember member = cardMemberMapper.requestToCardMember(entityRequest);
        member.setId(uuid);
        repository.save(member);
        return cardMemberMapper.cardMemberToResponse(member);
    }
}
