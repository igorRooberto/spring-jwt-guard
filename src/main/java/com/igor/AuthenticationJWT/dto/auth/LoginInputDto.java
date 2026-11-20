package com.igor.AuthenticationJWT.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginInputDto(

        @NotBlank(message = "O Email é obrigatório")
        String email,

        @NotBlank(message = "A Senha é obrigatória")
        String password) {
}
