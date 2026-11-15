package com.igor.AuthenticationJWT.repositories;

import com.igor.AuthenticationJWT.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<User, Long> {
}
