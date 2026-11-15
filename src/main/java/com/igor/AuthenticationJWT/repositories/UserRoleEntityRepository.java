package com.igor.AuthenticationJWT.repositories;

import com.igor.AuthenticationJWT.domain.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Long> {
}
