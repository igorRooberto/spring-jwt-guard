package com.igor.AuthenticationJWT.usecases;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.domain.entities.UserRoleEntity;
import com.igor.AuthenticationJWT.dto.auth.RegisterInputDto;
import com.igor.AuthenticationJWT.exception.custom.ConflictException;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import com.igor.AuthenticationJWT.repositories.UserRoleEntityRepository;
import com.igor.AuthenticationJWT.usecases.auth.RegisterUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RegisterUserUseCaseTest {

    private UserRepository userRepository;
    private UserRoleEntityRepository userRoleEntityRepository;
    private PasswordEncoder passwordEncoder;

    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        userRoleEntityRepository = mock(UserRoleEntityRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);

        registerUserUseCase = new RegisterUserUseCase(userRepository, userRoleEntityRepository, passwordEncoder);
    }

    @Test
    void execute_WhenValidInput_ShouldRegisterUserSuccessfully(){
        RegisterInputDto inputDto = new RegisterInputDto("igor@gmail.com", "iGor5678");

        User userSavedTest = User.builder()
                .id(1L)
                .build();

        UserRoleEntity userRoleEntityTest = new UserRoleEntity(1L, "ROLE_USER");

        when(userRepository.existsByEmail(inputDto.email())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(userSavedTest);
        when(passwordEncoder.encode(inputDto.password())).thenReturn("PASSWORD-ENCRYPTED");
        when(userRoleEntityRepository.findByName("USER")).thenReturn(Optional.of(userRoleEntityTest));

        Long userId = registerUserUseCase.execute(inputDto);

        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(userSavedTest.getId(), userId);
    }

    @Test
    void execute_WhenEmailAlreadyExists_ShouldThrowConflictException(){
        RegisterInputDto inputDto = new RegisterInputDto("igor@gmail.com", "iGor5678");

        when(userRepository.existsByEmail(inputDto.email())).thenReturn(true);

        ConflictException ex = assertThrows(ConflictException.class, () -> registerUserUseCase.execute(inputDto));

        assertEquals("O Email já está sendo utilizado", ex.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void execute_WhenRoleIsNotFound_ShouldThrowIllegalStateException(){
        RegisterInputDto inputDto = new RegisterInputDto("igor@gmail.com", "iGor5678");

        when(userRepository.existsByEmail(inputDto.email())).thenReturn(false);
        when(passwordEncoder.encode(inputDto.password())).thenReturn("PASSWORD-ENCRYPTED");
        when(userRoleEntityRepository.findByName("USER")).thenReturn(Optional.empty());

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> registerUserUseCase.execute(inputDto));

        assertEquals("Role não encontrada, ERRO INTERNO", ex.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }


}
