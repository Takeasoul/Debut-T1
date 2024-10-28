package org.example.debut.web.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.debut.entity.RoleType;

import java.util.Collection;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardMemberResponse {

    private UUID id;

    private String firstname;

    private String middlename;

    private String lastname;

    private String company;

    private String position;

    private String email;

    private String phone;


    private RoleType role;
}