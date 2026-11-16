package com.igor.AuthenticationJWT.controller;

import com.igor.AuthenticationJWT.dto.LoginInputDto;
import com.igor.AuthenticationJWT.dto.LoginResponseDto;
import com.igor.AuthenticationJWT.dto.RegisterInputDto;
import com.igor.AuthenticationJWT.services.LoginUserUseCase;
import com.igor.AuthenticationJWT.services.RegisterUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid RegisterInputDto inputDto){
        registerUserUseCase.execute(inputDto);

        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody @Valid LoginInputDto dto){
        LoginResponseDto responseDto  = loginUserUseCase.execute(dto);
        return ResponseEntity.ok().body(responseDto);
    }


}
