package com.igor.AuthenticationJWT.usecases;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.domain.entities.UserRoleEntity;
import com.igor.AuthenticationJWT.dto.auth.LoginInputDto;
import com.igor.AuthenticationJWT.dto.auth.LoginResponseDto;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import com.igor.AuthenticationJWT.security.JwtTokenService;
import com.igor.AuthenticationJWT.usecases.auth.LoginUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginUserUseCaseTest {

    private UserRepository userRepository;
    private JwtTokenService jwtService;
    private PasswordEncoder passwordEncoder;

    private LoginUserUseCase loginUserUseCase;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        jwtService = mock(JwtTokenService.class);
        passwordEncoder = mock(PasswordEncoder.class);

        loginUserUseCase = new LoginUserUseCase(userRepository, jwtService, passwordEncoder);
    }

    @Test
    void execute_WhenValidInput_ShouldReturnTokenSuccessfully(){

        LoginInputDto inputDto = new LoginInputDto("igor@gmail.com", "iGor5678");

        UserRoleEntity userRole = new UserRoleEntity(1L, "USER");
        Set<UserRoleEntity> userRoleEntitySet = Set.of(userRole);

        User user = User.builder()
                .id(1L)
                .password("PASSWORD-ENCRYPTED")
                .email("igor@gmail.com")
                .active(true)
                .roleEntitySet(userRoleEntitySet)
                .build();

        when(userRepository.findByEmail(inputDto.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(inputDto.password(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("TOKEN_JWT");

        LoginResponseDto responseDto = loginUserUseCase.execute(inputDto);
    }

    @Test
    void execute_WhenInValidEmail_ShouldThrowException(){

        LoginInputDto inputDto = new LoginInputDto("igor@gmail.com", "iGor5678");

        when(userRepository.findByEmail(inputDto.email())).thenReturn(Optional.empty());

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> loginUserUseCase.execute(inputDto));

        assertEquals("Credenciais Inválidas", exception.getMessage());
    }

    @Test
    void execute_WhenInvalidPassword_ShouldThrowException(){

        LoginInputDto inputDto = new LoginInputDto("igor@gmail.com", "iGor5678");

        UserRoleEntity userRole = new UserRoleEntity(1L, "USER");
        Set<UserRoleEntity> userRoleEntitySet = Set.of(userRole);

        User user = User.builder()
                .id(1L)
                .password("PASSWORD-ENCRYPTED")
                .email("igor@gmail.com")
                .active(true)
                .roleEntitySet(userRoleEntitySet)
                .build();

        when(userRepository.findByEmail(inputDto.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(inputDto.password(), user.getPassword())).thenReturn(false);

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> loginUserUseCase.execute(inputDto));

        assertEquals("Credenciais Inválidas", exception.getMessage());
    }

    @Test
    void execute_WhenUserIsDisabled_ShouldThrowException(){

        LoginInputDto inputDto = new LoginInputDto("igor@gmail.com", "iGor5678");

        UserRoleEntity userRole = new UserRoleEntity(1L, "USER");
        Set<UserRoleEntity> userRoleEntitySet = Set.of(userRole);

        User user = User.builder()
                .id(1L)
                .password("PASSWORD-ENCRYPTED")
                .email("igor@gmail.com")
                .active(false)
                .roleEntitySet(userRoleEntitySet)
                .build();

        when(userRepository.findByEmail(inputDto.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(inputDto.password(), user.getPassword())).thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> loginUserUseCase.execute(inputDto));

        assertEquals("Sua Conta encontra-se desativada", exception.getMessage());
    }
}
