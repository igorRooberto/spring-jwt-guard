package com.igor.AuthenticationJWT.usecases.admin;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.domain.entities.UserRoleEntity;
import com.igor.AuthenticationJWT.dto.admin.AdminUserDetailsDTO;
import com.igor.AuthenticationJWT.exception.custom.NotFoundException;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FindUserByIdUseCaseTest {

    private UserRepository userRepository;
    private FindUserByIdUseCase findUserByIdUseCase;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        findUserByIdUseCase = new FindUserByIdUseCase(userRepository);
    }

    @Test
    void shouldFindUserSuccessfully(){

        UserRoleEntity role = new UserRoleEntity(1L, "ADMIN");
        Set<UserRoleEntity> roles = Set.of(role);

        User user = User.builder()
                .id(1L)
                .email("igor@gmail.com")
                .roleEntitySet(roles)
                .createdAt(Instant.now())
                .active(true)
                .build();

        when(userRepository.findByIdWithRoles(1L)).thenReturn(Optional.of(user));

        AdminUserDetailsDTO userDetailsDTO = findUserByIdUseCase.execute(1L);

        assertEquals(user.getId(), userDetailsDTO.id());
        assertEquals(user.getEmail(), userDetailsDTO.email());
        assertEquals(user.getActive(), userDetailsDTO.active());
        assertEquals(user.getRoles(), userDetailsDTO.roles());
        assertEquals(user.getCreatedAt(), userDetailsDTO.createdAt());

        verify(userRepository).findByIdWithRoles(1L);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserDoesNotExist(){
        when(userRepository.findByIdWithRoles(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> findUserByIdUseCase.execute(1L));

        assertEquals("Usuário não encontrado", notFoundException.getMessage());
        verify(userRepository).findByIdWithRoles(1L);
    }
}
