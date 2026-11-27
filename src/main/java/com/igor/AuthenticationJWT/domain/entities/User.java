package com.igor.AuthenticationJWT.domain.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

    @Column(name = "active",nullable = false)
    private Boolean active;

    @ManyToMany()
    @JoinTable(name = "tb_user_role",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRoleEntity> roleEntitySet = new HashSet<>();

    protected User() {
    }

    @Builder
    public User(Long id, String email, String password, Instant createdAt, Instant updatedAt, Boolean active, Set<UserRoleEntity> roleEntitySet) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        this.roleEntitySet = roleEntitySet;
    }

    public List<String> getRoles(){
        return roleEntitySet.stream().map(UserRoleEntity::getName).toList();
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
    }

    public void deactivateUser(){
        this.active = false;
    }

}
