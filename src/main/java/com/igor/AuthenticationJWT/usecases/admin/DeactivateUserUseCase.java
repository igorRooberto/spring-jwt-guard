package com.igor.AuthenticationJWT.usecases.admin;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.exception.NotFoundException;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeactivateUserUseCase {

    private final UserRepository userRepository;

    @Transactional
    public void execute(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        user.deactivateUser();
    }
}
