package com.igor.AuthenticationJWT.controller;

import com.igor.AuthenticationJWT.dto.admin.AdminResponseDTO;
import com.igor.AuthenticationJWT.dto.admin.AdminUserDetailsDTO;
import com.igor.AuthenticationJWT.usecases.admin.DeactivateUserUseCase;
import com.igor.AuthenticationJWT.usecases.admin.FindAllUsersUseCase;
import com.igor.AuthenticationJWT.usecases.admin.FindUserByIdUseCase;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final FindAllUsersUseCase findAllUsersUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final DeactivateUserUseCase deactivateUserUseCase;

    @GetMapping("/users")
    public ResponseEntity<Page<AdminResponseDTO>> findAllUsers(
            @RequestParam(name = "page",defaultValue = "0")
            @Min(0) int page,

            @RequestParam(name = "size",defaultValue = "10")
            @Min(1) @Max(100) int size
    )     {
          return ResponseEntity.ok(findAllUsersUseCase.execute(page, size));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AdminUserDetailsDTO> findUserById(@PathVariable Long id){
        return ResponseEntity.ok(findUserByIdUseCase.execute(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id){
        deactivateUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
