package com.igor.AuthenticationJWT.controller;

import com.igor.AuthenticationJWT.dto.user.UserResponseDTO;
import com.igor.AuthenticationJWT.usecases.user.GetUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final GetUserUseCase getUserUseCase;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUser(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getName();

        return ResponseEntity.ok(getUserUseCase.execute(Long.valueOf(userId)));
    }



}
