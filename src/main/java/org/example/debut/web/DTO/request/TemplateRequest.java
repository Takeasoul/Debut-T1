package org.example.debut.web.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRequest {

    @NotNull
    private String name;

    @NotNull
    private UUID roleId;
}
