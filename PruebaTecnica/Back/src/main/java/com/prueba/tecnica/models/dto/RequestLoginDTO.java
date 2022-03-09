package com.prueba.tecnica.models.dto;

import lombok.*;

/**
 * DTO para request de login.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class RequestLoginDTO {
    private String userName;
    private String password;
}
