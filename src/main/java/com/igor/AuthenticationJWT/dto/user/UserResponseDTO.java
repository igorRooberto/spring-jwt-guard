package com.igor.AuthenticationJWT.dto.user;

import java.time.Instant;
import java.util.List;

public record UserResponseDTO(

        Long id,
        String email,
        Instant createdAt,
        List<String> roles
) {
}
