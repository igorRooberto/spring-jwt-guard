package com.igor.AuthenticationJWT.dto.admin;

import java.time.Instant;
import java.util.List;

public record AdminUserDetailsDTO(

        Long id,
        String email,
        boolean active,
        List<String> roles,
        Instant createdAt
) {
}
