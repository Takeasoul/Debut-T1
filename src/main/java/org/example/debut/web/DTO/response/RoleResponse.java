package org.example.debut.web.DTO.response;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    @NotNull
    private UUID roleId;

    @NotNull
    private String role;
}
