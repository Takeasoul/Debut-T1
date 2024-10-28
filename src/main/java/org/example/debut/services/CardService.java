package org.example.debut.services;



import org.example.debut.web.DTO.request.CardRequest;
import org.example.debut.web.DTO.response.CardResponse;

import java.util.UUID;

public interface CardService extends EntityService<CardResponse, CardRequest, UUID> {
}
