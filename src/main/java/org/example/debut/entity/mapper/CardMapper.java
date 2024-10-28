package org.example.debut.entity.mapper;



import org.example.debut.entity.Card;
import org.example.debut.web.DTO.request.CardRequest;
import org.example.debut.web.DTO.response.CardResponse;
import org.mapstruct.*;

@DecoratedWith(CardMapperDelegate.class)
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CardMapper {
    Card requestToCard(CardRequest request);

    CardResponse cardToResponse(Card card);
}
