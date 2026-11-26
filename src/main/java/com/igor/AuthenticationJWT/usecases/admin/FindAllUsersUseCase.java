package com.igor.AuthenticationJWT.usecases.admin;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.dto.admin.AdminResponseDTO;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FindAllUsersUseCase {

    private final UserRepository userRepository;

    public Page<AdminResponseDTO> execute(int page, int size){

        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by("id").ascending());

        Page<User> pageUser = userRepository.findAll(pageable);

        return pageUser.map(u -> new AdminResponseDTO(u.getId(), u.getEmail(), u.getActive()));
    }
}
