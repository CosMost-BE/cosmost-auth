package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.PutAuthRequest;

/**
 * Create
 */
public interface AuthService {
    String createAuth(CreateAuthRequest createAuthRequest);

    String putAuth(PutAuthRequest putAuthRequest);

    default AuthEntity dtoEntity(CreateAuthRequest createAuthRequest){
        AuthEntity auth = AuthEntity.builder()
                .loginId(createAuthRequest.getLoginId())
                .loginPwd(createAuthRequest.getLoginPwd())
                .email(createAuthRequest.getEmail())
                .authRole(AuthRole.USER)
                .authStatus(AuthStatus.ACTIVE)
                .nickName(createAuthRequest.getNickName())
                .address(createAuthRequest.getAddress())
                .birthdate(createAuthRequest.getBirthdate())
                .build();
        return auth;
    };

    default AuthEntity dtoEntity(PutAuthRequest putAuthRequest) {
        AuthEntity auth = AuthEntity.builder()
                .loginId(putAuthRequest.getLoginId())
                .loginPwd(putAuthRequest.getLoginPwd())
                .build();
        return auth;
    }
}
