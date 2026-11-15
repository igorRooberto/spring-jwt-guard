package com.igor.AuthenticationJWT.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterInputDto(

        @NotBlank(message = "O Email é obrigatório")
        String email,

        @NotBlank(message = "A Senha é obrigatória")
        String password
) {
}
