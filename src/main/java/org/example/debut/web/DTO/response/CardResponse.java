package org.example.debut.web.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.debut.entity.CardMember;
import org.example.debut.entity.TemplateType;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    private UUID id;

    private UUID cardMemberId;

    private TemplateResponse templateType;

}