package com.example.project.auth.infrastructure.repository;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthEntityRepository extends JpaRepository<AuthEntity, Long> {
    Optional<AuthEntity> findById(Long id);
    AuthEntity findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

}
