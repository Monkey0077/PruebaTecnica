package com.prueba.tecnica.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * DTO para retornar la informacion de un usuario logueado.
 */
@Builder
@Data
public class UserLoggedDTO {
    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expires_in")
    private int expiresIn;

    @JsonProperty(value = "issued_at")
    private String issuedAt;

    @JsonProperty(value = "user")
    private String user;

    @JsonProperty(value = "userId")
    private Long userId;
}
