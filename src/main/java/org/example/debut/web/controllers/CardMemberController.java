package org.example.debut.web.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.example.debut.services.CardMembersService;
import org.example.debut.web.DTO.request.CardMemberRequest;
import org.example.debut.web.DTO.response.CardMemberResponse;
import org.example.debut.web.DTO.response.ModelListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;


@RestController
@RequiredArgsConstructor
@Tag(name = "Card-members", description = "Участники")
@RequestMapping(path= "api/v1/card-members",produces = "application/json")
@CrossOrigin()
public class CardMemberController {

    private final CardMembersService cardMemberService;

    @GetMapping("/{id}")
    public ResponseEntity<CardMemberResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                cardMemberService.findById(id)
        );
    }

    @PostMapping
    public ResponseEntity<CardMemberResponse> createCardMember(@RequestBody CardMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardMemberService.create(request));
    }

    @GetMapping
    public ResponseEntity<ModelListResponse<CardMemberResponse>> getAllCardMembers(Pageable pageable) {
        try {
            return ResponseEntity.ok(cardMemberService.findAll(pageable).get());
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardMemberResponse> updateCard(@PathVariable UUID id, @RequestBody CardMemberRequest request) {
        return ResponseEntity.ok(cardMemberService.update(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardMember(@PathVariable UUID id) {
        cardMemberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


