package org.example.debut.services.impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.debut.config.AccessDeniedException;
import org.example.debut.entity.Card;
import org.example.debut.entity.CardMember;
import org.example.debut.entity.TemplateType;
import org.example.debut.entity.mapper.CardMapper;
import org.example.debut.repositories.CardMemberRepository;
import org.example.debut.repositories.CardRepository;
import org.example.debut.repositories.TemplateTypeRepository;
import org.example.debut.services.CardService;
import org.example.debut.web.DTO.request.CardRequest;
import org.example.debut.web.DTO.response.CardResponse;
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
public class CardServiceImpl implements CardService {

    private final CardMapper cardMapper;

    private final CardRepository repository;

    private final TemplateTypeRepository templateTypeRepository;
    private final CardMemberRepository cardMemberRepository;

    @Override
    @Transactional
    @Async
    public CompletableFuture<ModelListResponse<CardResponse>> findAll(Pageable pageable) {
        log.info("Find all cards");
        Page<Card> cards = repository.findAll(pageable);
        return CompletableFuture.completedFuture(ModelListResponse.<CardResponse>builder()
                .totalCount(cards.getTotalElements())
                .data(cards.stream().map(cardMapper::cardToResponse).toList())
                .build());

    }

    @Override
    @Transactional
    public CardResponse findById(UUID id) {
        log.info("Find card with ID: {}", id);
        return cardMapper.cardToResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Card with ID {0} not found!", id)
                )));
    }


    @Override
    @Transactional
    public CardResponse create(CardRequest entityRequest) {
        log.info("Create card {}", entityRequest);

        TemplateType templateType = templateTypeRepository.findById(entityRequest.getTemplateTypeId())
                .orElseThrow(EntityNotFoundException::new);
        CardMember cardMember = cardMemberRepository.findById(entityRequest.getMemberId())
                .orElseThrow(EntityNotFoundException::new);

        if (templateType.getRoleType().equals(cardMember.getRole())) {
            Card card = cardMapper.requestToCard(entityRequest);
            card.setTemplateType(templateType);
            card.setCardMember(cardMember);

            return cardMapper.cardToResponse(repository.save(card));
        }

        throw new AccessDeniedException("User does not have rights to create this card");
    }

    @Override
    @Transactional
    public CardResponse update(UUID uuid, CardRequest entityRequest) {
        TemplateType templateType = templateTypeRepository.findById(entityRequest.getTemplateTypeId())
                .orElseThrow(EntityNotFoundException::new);
        CardMember cardMember = cardMemberRepository.findById(entityRequest.getMemberId())
                .orElseThrow(EntityNotFoundException::new);
        if (templateType.getRoleType().equals(cardMember.getRole())) {
            Card card = cardMapper.requestToCard(entityRequest);
            card.setId(uuid);
            repository.save(card);
            return cardMapper.cardToResponse(card);
        }
        throw new AccessDeniedException("User does not have rights to create this card");
    }



    @Override
    @Transactional
    public void deleteById(UUID id) {
        log.info("Delete card with ID: {}", id);
        Card card = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with ID: " + id));

        card.setCardMember(null);
        card.setTemplateType(null);
        repository.save(card);
        repository.deleteById(id);
        log.info("Card deleted successfully");

        if (repository.findById(id).isEmpty()) {
            log.info("Card with ID: {} was successfully deleted from the database", id);
        } else {
            log.error("Card with ID: {} still exists in the database", id);
        }
    }
}
