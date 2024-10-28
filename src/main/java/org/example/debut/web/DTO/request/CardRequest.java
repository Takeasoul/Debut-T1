package org.example.debut.web.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.debut.entity.TemplateType;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {


    @NotNull
    private UUID memberId;

    @NotNull
    private UUID templateTypeId;
}