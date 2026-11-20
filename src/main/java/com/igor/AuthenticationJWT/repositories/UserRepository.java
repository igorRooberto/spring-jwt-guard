package com.igor.AuthenticationJWT.repositories;

import com.igor.AuthenticationJWT.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.rolesEntitySet WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);

}
