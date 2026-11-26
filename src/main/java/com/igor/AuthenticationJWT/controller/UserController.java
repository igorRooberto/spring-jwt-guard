package com.igor.AuthenticationJWT.controller;

import com.igor.AuthenticationJWT.dto.user.ChangePasswordRequestDto;
import com.igor.AuthenticationJWT.dto.user.UserResponseDTO;
import com.igor.AuthenticationJWT.usecases.user.ChangePasswordUseCase;
import com.igor.AuthenticationJWT.usecases.user.FindMyProfileUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final FindMyProfileUseCase findMyProfileUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUser(@AuthenticationPrincipal String userId){
        return ResponseEntity.ok(findMyProfileUseCase.execute(Long.valueOf(userId)));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal String userId,
                                               @RequestBody @Valid ChangePasswordRequestDto requestDto){
        changePasswordUseCase.execute(Long.valueOf(userId), requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
