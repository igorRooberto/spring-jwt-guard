package com.igor.AuthenticationJWT.usecases.admin;

import com.igor.AuthenticationJWT.domain.entities.User;
import com.igor.AuthenticationJWT.exception.custom.NotFoundException;
import com.igor.AuthenticationJWT.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeactivateUserUseCaseTest {

    private UserRepository userRepository;
    private DeactivateUserUseCase deactivateUserUseCase;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        deactivateUserUseCase = new DeactivateUserUseCase(userRepository);
    }

    @Test
    void  shouldDeactivateUserSuccessfully(){

        User user = User.builder()
                .id(1L)
                .active(true)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        deactivateUserUseCase.execute(1L);

        assertEquals(false, user.getActive());
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserDoesNotExist(){

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> deactivateUserUseCase.execute(1L));

        assertEquals("Usuário não encontrado", notFoundException.getMessage());
        verify(userRepository).findById(1L);
    }

}
