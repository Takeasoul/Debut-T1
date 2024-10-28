package org.example.debut.services;



import org.example.debut.web.DTO.request.CardMemberRequest;
import org.example.debut.web.DTO.response.CardMemberResponse;

import java.util.UUID;

public interface CardMembersService extends  EntityService<CardMemberResponse, CardMemberRequest, UUID>{
}
