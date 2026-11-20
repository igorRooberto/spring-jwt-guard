package com.igor.AuthenticationJWT.usecases.user;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.dto.user.UserResponseDTO;
import com.igor.AuthenticationJWT.exception.NotFoundException;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserUseCase {

    private final UserRepository userRepository;

    public UserResponseDTO execute(Long id){
        User user = userRepository.findByIdWithRoles(id)
                .orElseThrow(() -> new NotFoundException("Usuário Não Encontrado"));

        return new UserResponseDTO(user.getId(), user.getEmail(), user.getCreatedAt(), user.getRoles());
    }
}
