package org.example.debut.entity.mapper;



import jakarta.persistence.EntityNotFoundException;

import org.example.debut.entity.Card;
import org.example.debut.entity.CardMember;
import org.example.debut.entity.RoleType;
import org.example.debut.repositories.CardRepository;
import org.example.debut.repositories.RoleRepository;
import org.example.debut.web.DTO.request.CardMemberRequest;
import org.example.debut.web.DTO.response.CardMemberResponse;
import org.example.debut.web.DTO.response.CardResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;

public abstract class CardMemberMapperDelegate implements CardMemberMapper {

    @Autowired
    private CardMemberMapper delegate;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public CardMember requestToCardMember(CardMemberRequest request){
        CardMember cardMember = delegate.requestToCardMember(request);

     cardMember.setRole(roleRepository.findById(request.getRoleId()).orElseThrow(EntityNotFoundException::new));
        return cardMember;
    }

    @Override
    public CardMemberResponse cardMemberToResponse(CardMember cardMember) {

        CardMemberResponse response = delegate.cardMemberToResponse(cardMember);
        response.setRole(cardMember.getRole());
        return response;
    }

}
