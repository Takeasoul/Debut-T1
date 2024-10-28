package org.example.debut.web.DTO.response;

import lombok.*;

@Getter
@Data
@NoArgsConstructor
@Builder

public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

}