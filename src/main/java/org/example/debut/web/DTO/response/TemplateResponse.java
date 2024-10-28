package org.example.debut.web.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TemplateResponse {

    private UUID id;
    private String name;
    private String role;
}
