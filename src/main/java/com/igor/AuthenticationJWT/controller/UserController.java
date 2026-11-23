package com.igor.AuthenticationJWT.controller;

import com.igor.AuthenticationJWT.dto.user.ChangePasswordRequestDto;
import com.igor.AuthenticationJWT.dto.user.UserResponseDTO;
import com.igor.AuthenticationJWT.usecases.user.ChangePasswordUseCase;
import com.igor.AuthenticationJWT.usecases.user.GetUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final GetUserUseCase getUserUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUser(@AuthenticationPrincipal String userId){
        return ResponseEntity.ok(getUserUseCase.execute(Long.valueOf(userId)));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal String userId,
                                               @RequestBody @Valid ChangePasswordRequestDto requestDto){
        changePasswordUseCase.execute(Long.valueOf(userId), requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
