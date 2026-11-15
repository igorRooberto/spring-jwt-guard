package com.igor.AuthenticationJWT.repositories;

import com.igor.AuthenticationJWT.domain.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity> findByName(String name);
}
