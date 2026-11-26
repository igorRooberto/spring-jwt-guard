package com.igor.AuthenticationJWT.usecases.admin;

import com.igor.AuthenticationJWT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeactivateUserUseCase {

    private final UserRepository userRepository;
}
