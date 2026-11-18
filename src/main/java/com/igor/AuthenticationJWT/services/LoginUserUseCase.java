package com.igor.AuthenticationJWT.services;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.dto.LoginInputDto;
import com.igor.AuthenticationJWT.dto.LoginResponseDto;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import com.igor.AuthenticationJWT.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto execute(LoginInputDto dto){

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new BadCredentialsException("Credenciais Inválidas"));

        if(!passwordEncoder.matches(dto.password(), user.getPassword())){
            throw new BadCredentialsException("Credenciais Inválidas");
        }

        if(!user.getActive()){
            throw new IllegalStateException("Sua Conta encontra-se desativada");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponseDto(token);
    }
}
