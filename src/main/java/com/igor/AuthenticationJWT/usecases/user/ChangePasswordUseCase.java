package com.igor.AuthenticationJWT.usecases.user;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.dto.user.ChangePasswordRequestDto;
import com.igor.AuthenticationJWT.exception.BadRequestException;
import com.igor.AuthenticationJWT.exception.ConflictException;
import com.igor.AuthenticationJWT.exception.NotFoundException;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(Long userId, ChangePasswordRequestDto dto){

        if (dto.currentPassword().equals(dto.newPassword())){
            throw new ConflictException("A nova senha não pode ser igual à senha atual.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário Não Encontrado"));

        if(!passwordEncoder.matches(dto.currentPassword(), user.getPassword())){
            throw new BadRequestException("Senha atual inválida");
        }

        String encryptedPassword = passwordEncoder.encode(dto.newPassword());
        user.changePassword(encryptedPassword);
    }
}
