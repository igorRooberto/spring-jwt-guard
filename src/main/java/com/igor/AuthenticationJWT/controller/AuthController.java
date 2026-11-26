package com.igor.AuthenticationJWT.controller;

import com.igor.AuthenticationJWT.dto.auth.LoginInputDto;
import com.igor.AuthenticationJWT.dto.auth.LoginResponseDto;
import com.igor.AuthenticationJWT.dto.auth.RegisterInputDto;
import com.igor.AuthenticationJWT.usecases.auth.LoginUserUseCase;
import com.igor.AuthenticationJWT.usecases.auth.RegisterUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid RegisterInputDto inputDto){
        Long userId = registerUserUseCase.execute(inputDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("users/{id}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody @Valid LoginInputDto dto){
        LoginResponseDto responseDto  = loginUserUseCase.execute(dto);
        return ResponseEntity.ok().body(responseDto);
    }


}
