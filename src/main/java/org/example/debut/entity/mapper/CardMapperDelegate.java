package org.example.debut.entity.mapper;




import jakarta.persistence.EntityNotFoundException;
import org.example.debut.entity.Card;
import org.example.debut.entity.TemplateType;
import org.example.debut.repositories.CardMemberRepository;
import org.example.debut.repositories.CardRepository;
import org.example.debut.repositories.TemplateTypeRepository;
import org.example.debut.web.DTO.request.CardRequest;
import org.example.debut.web.DTO.response.CardResponse;
import org.example.debut.web.DTO.response.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public abstract class CardMapperDelegate implements CardMapper {

    @Autowired
    private CardMapper delegate;

    @Autowired
    private CardMemberRepository repository;

    @Autowired
    private TemplateTypeRepository templateTypeRepository;


    @Override
    public CardResponse cardToResponse(Card card) {
        CardResponse response = delegate.cardToResponse(card);
        response.setId(card.getId());
        response.setCardMemberId(card.getCardMember().getId());
        TemplateResponse templateResponse = new TemplateResponse();
        templateResponse.setId(card.getTemplateType().getId());
        templateResponse.setName(card.getTemplateType().getName());
        templateResponse.setRole(card.getTemplateType().getRoleType().getRole());
        response.setTemplateType(templateResponse);
        return response;
    }

    @Override
    public Card requestToCard(CardRequest request){
       Card card = delegate.requestToCard(request);
        card.setCardMember(repository.findById(request.getMemberId()).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Member with ID {0} not found!", request.getMemberId())
        )));

        card.setTemplateType(templateTypeRepository.findById(request.getTemplateTypeId()).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Template type with ID {0} not found!", request.getMemberId())
        )));

        return card;
    }

}
