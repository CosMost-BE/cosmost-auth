package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import com.example.project.auth.requestbody.CreateAuthRequest;

/**
 * Create
 */
public interface AuthService {
    Long createAuth(CreateAuthRequest createAuthRequest);
    default AuthEntity dtoEntity(CreateAuthRequest createAuthRequest){
        AuthEntity auth = AuthEntity.builder()
                .loginId(createAuthRequest.getLoginId())
                .loginPwd(createAuthRequest.getLoginPwd())
                .name(createAuthRequest.getName())
                .email(createAuthRequest.getEmail())
                .authRole(AuthRole.USER)
                .authStatus(AuthStatus.ACTIVE)
                .nickName(createAuthRequest.getNickName())
                .address(createAuthRequest.getAddress())
                .birthdate(createAuthRequest.getBirthdate())
                .married(createAuthRequest.isMarried())
                .build();
        return auth;
    };
}
