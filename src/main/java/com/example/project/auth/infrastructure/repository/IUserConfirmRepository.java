package com.example.project.auth.infrastructure.repository;

import com.example.project.auth.infrastructure.entity.UserConfirmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserConfirmRepository extends JpaRepository<UserConfirmEntity, Long> {
    UserConfirmEntity findByEmail(String email);

}
