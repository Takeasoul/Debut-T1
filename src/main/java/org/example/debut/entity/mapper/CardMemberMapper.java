package org.example.debut.entity.mapper;



import org.example.debut.entity.CardMember;
import org.example.debut.web.DTO.request.CardMemberRequest;
import org.example.debut.web.DTO.response.CardMemberResponse;
import org.mapstruct.*;

@DecoratedWith(CardMemberMapperDelegate.class)
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CardMemberMapper {
    CardMember requestToCardMember(CardMemberRequest request);
    CardMemberResponse cardMemberToResponse(CardMember cardMember);

}
