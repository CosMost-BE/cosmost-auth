package com.example.project.auth.infrastructure.repository;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.UserConfirmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserConfirmRepository extends JpaRepository<UserConfirmEntity, Long> {
    UserConfirmEntity findByEmail(String email);
//    Optional<AuthEntity> findByLoginId(String loginId);

}
