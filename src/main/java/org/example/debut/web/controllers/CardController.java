package org.example.debut.web.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.example.debut.config.AccessDeniedException;
import org.example.debut.services.CardService;
import org.example.debut.web.DTO.request.CardRequest;
import org.example.debut.web.DTO.response.CardResponse;
import org.example.debut.web.DTO.response.ErrorResponse;
import org.example.debut.web.DTO.response.ModelListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Cards", description = "Карты")
@RequestMapping(path="api/v1/cards",produces = "application/json")
@CrossOrigin()
public class CardController {
    private final CardService cardService;

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                cardService.findById(id)
        );
    }

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody CardRequest request) {
            CardResponse response = cardService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ModelListResponse<CardResponse>> getAllCards(Pageable pageable) {
        try {
            return ResponseEntity.ok(cardService.findAll(pageable).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
    }}

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCard(@PathVariable UUID id, @RequestBody CardRequest request) {
        return ResponseEntity.ok(cardService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable UUID id) {
        cardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}