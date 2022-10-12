package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.PutAuthRequest;

/**
 * Create
 */
public interface AuthService {
    String createAuth(CreateAuthRequest createAuthRequest);

    String putAuth(PutAuthRequest putAuthRequest);

    default AuthEntity signUpDtoEntity(CreateAuthRequest createAuthRequest){
        AuthEntity auth = AuthEntity.builder()
                .loginId(createAuthRequest.getLoginId())
                .loginPwd(createAuthRequest.getLoginPwd())
                .email(createAuthRequest.getEmail())
                .authRole(AuthRole.USER)
                .authStatus(AuthStatus.ACTIVE)
                .nickName(createAuthRequest.getNickName())
                .address(createAuthRequest.getAddress())
                .authSns(createAuthRequest.getAuthSns())
                .authMarried(createAuthRequest.getAuthMarried())
                .ageGroup(createAuthRequest.getAgeGroup())
                .profileImgOriginName(createAuthRequest.getProfileImgOriginName())
                .profileImgSaveName(createAuthRequest.getProfileImgSaveName())
                .profileImgSaveUrl(createAuthRequest.getProfileImgSaveUrl())
                .build();
        return auth;
    };

    default AuthEntity loginDtoEntity(PutAuthRequest putAuthRequest) {
        AuthEntity auth = AuthEntity.builder()
                .loginId(putAuthRequest.getLoginId())
                .loginPwd(putAuthRequest.getLoginPwd())
                .build();
        return auth;
    }
}
