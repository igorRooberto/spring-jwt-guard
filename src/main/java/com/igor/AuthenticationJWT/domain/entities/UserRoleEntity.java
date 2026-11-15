package com.igor.AuthenticationJWT.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "tb_role")
@Getter
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,updatable = false)
    private Long id;

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    protected UserRoleEntity() {
    }
}
