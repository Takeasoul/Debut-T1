package org.example.debut.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.debut.entity.RoleType;
import org.example.debut.services.CardMembersService;
import org.example.debut.web.DTO.request.CardMemberRequest;
import org.example.debut.web.DTO.response.CardMemberResponse;
import org.example.debut.web.DTO.response.ModelListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardMemberController.class)
class CardMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardMembersService cardMembersService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID cardMemberId;
    private CardMemberRequest cardMemberRequest;
    private CardMemberResponse cardMemberResponse;

    @BeforeEach
    void setUp() {
        cardMemberId = UUID.randomUUID();
        RoleType roleType = new RoleType();
        roleType.setRole("ADMIN");
        roleType.setId(UUID.randomUUID());
        cardMemberRequest = new CardMemberRequest("John", "Jr", "Ivanov", "Company A", "Manager", "john@gmail.com", "1234567890", UUID.randomUUID());
        cardMemberResponse = new CardMemberResponse(cardMemberId, "Sasha", "Jr", "Ivano", "Company B", "Student", "john@gmail.com", "1234567890", roleType);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_ShouldReturnCardMember() throws Exception {
        Mockito.when(cardMembersService.findById(cardMemberId)).thenReturn(cardMemberResponse);

        mockMvc.perform(get("/api/v1/card-members/{id}", cardMemberId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cardMemberId.toString()))
                .andExpect(jsonPath("$.firstname").value("Sasha"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCardMember_ShouldCreateCardMember() throws Exception {
        Mockito.when(cardMembersService.create(any(CardMemberRequest.class))).thenReturn(cardMemberResponse);

        mockMvc.perform(post("/api/v1/card-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardMemberRequest))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cardMemberId.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllCardMembers_ShouldReturnListOfCardMembers() throws Exception {
        ModelListResponse<CardMemberResponse> response = ModelListResponse.<CardMemberResponse>builder()
                .data(Arrays.asList(cardMemberResponse)) // Список участников
                .totalCount(1L) // Общее количество участников
                .build();

        Mockito.when(cardMembersService.findAll(any(Pageable.class))).thenReturn(java.util.concurrent.CompletableFuture.completedFuture(response));

        mockMvc.perform(get("/api/v1/card-members"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(cardMemberResponse.getId().toString()))
                .andExpect(jsonPath("$.totalCount").value(1)); // Проверка общего количества
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCard_ShouldUpdateCardMember() throws Exception {
        Mockito.when(cardMembersService.update(eq(cardMemberId), any(CardMemberRequest.class))).thenReturn(cardMemberResponse);

        mockMvc.perform(put("/api/v1/card-members/{id}", cardMemberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardMemberRequest))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cardMemberId.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCardMember_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/card-members/{id}", cardMemberId)
                .with(csrf()))
                .andExpect(status().isNoContent());

        Mockito.verify(cardMembersService, Mockito.times(1)).deleteById(cardMemberId);
    }
}
