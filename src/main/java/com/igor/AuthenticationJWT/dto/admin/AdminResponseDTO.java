package com.igor.AuthenticationJWT.dto.admin;


public record AdminResponseDTO(

        Long id,
        String email,
        boolean active
) {
}
