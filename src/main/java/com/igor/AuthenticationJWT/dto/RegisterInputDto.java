package com.igor.AuthenticationJWT.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterInputDto(

        @NotBlank(message = "O Email é obrigatório")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
                message = "O email deve ser um endereço @gmail.com válido"
        )
        String email,

        @NotBlank(message = "A Senha é obrigatória")
        @Pattern(
                regexp = "^(?=.*[A-Z]).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres e pelo menos uma letra maiúscula"
        )
        String password
) {
}
