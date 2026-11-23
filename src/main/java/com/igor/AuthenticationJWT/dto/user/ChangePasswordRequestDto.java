package com.igor.AuthenticationJWT.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequestDto(

        @NotBlank(message = "A senha atual é obrigatória")
        String currentPassword,

        @NotBlank(message = "A nova senha é obrigatória")
        @Pattern(
                regexp = "^(?=.*[A-Z]).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres e pelo menos uma letra maiúscula"
        )
        String newPassword
) {
}
