package com.example.project.auth.infrastructure.repository;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.requestbody.CreateAuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthEntityRepository extends JpaRepository<AuthEntity, Long> {

//    boolean existByLoginId(String loginId);
//    boolean existByEmail(String email);
    Optional<AuthEntity> findById(Long id);
    CreateAuthRequest findByLoginId(String loginId);
}
