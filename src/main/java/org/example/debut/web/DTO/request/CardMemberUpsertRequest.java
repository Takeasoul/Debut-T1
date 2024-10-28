package org.example.debut.web.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardMemberUpsertRequest {

    @NotNull
    private UUID id;

    @NotNull
    private String firstname;

    @NotNull
    private String middlename;

    @NotNull
    private String lastname;

    @NotNull
    private String company;

    @NotNull
    private String position;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private UUID roleId;

}