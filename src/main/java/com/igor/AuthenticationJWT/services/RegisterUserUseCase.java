package com.igor.AuthenticationJWT.services;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.domain.entities.UserRoleEntity;
import com.igor.AuthenticationJWT.dto.RegisterInputDto;
import com.igor.AuthenticationJWT.exception.ConflictException;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import com.igor.AuthenticationJWT.repositories.UserRoleEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final UserRoleEntityRepository userRoleEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public void execute(RegisterInputDto dto){

        if(userRepository.existsByEmail(dto.email())){
            throw new ConflictException("O Email já está sendo utilizado");
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());

        UserRoleEntity role = userRoleEntityRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role USER não encontrada, verifique o Banco de dados"));

        Set<UserRoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(role);

        User user = User.builder()
                .email(dto.email())
                .password(encryptedPassword)
                .active(true)
                .roleEntitySet(roleEntitySet)
                .build();

        userRepository.save(user);
    }



}
