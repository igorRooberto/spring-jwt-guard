package com.igor.AuthenticationJWT.usecases.admin;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.dto.admin.AdminUserDetailsDTO;
import com.igor.AuthenticationJWT.exception.NotFoundException;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserByIdUseCase {

    private final UserRepository userRepository;

    public AdminUserDetailsDTO execute(Long id){

        User user = userRepository.findByIdWithRoles(id)
                .orElseThrow(() -> new NotFoundException("Usuário Não encontrado"));

        return new AdminUserDetailsDTO(user.getId(), user.getEmail(), user.getActive(), user.getRoles(), user.getCreatedAt());
    }
}
