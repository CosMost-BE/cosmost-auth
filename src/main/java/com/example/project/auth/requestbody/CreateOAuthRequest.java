package com.example.project.auth.requestbody;

import com.example.project.auth.infrastructure.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOAuthRequest {

    private String nickname;

    private String email;

    private String ageGroup;

    private String address;

    private AuthMarried married;

    private AuthSns sns;

    private AuthRole role;

    private AuthStatus status;

    public AuthEntity oauthDtoEntity(CreateOAuthRequest createOAuthRequest) {
        return AuthEntity.builder()
                .nickname(createOAuthRequest.getNickname())
                .email(createOAuthRequest.getEmail())
                .ageGroup(createOAuthRequest.getAgeGroup())
                .address(createOAuthRequest.getAddress())
                .married(createOAuthRequest.getMarried())
                .sns(AuthSns.YES)
                .role(AuthRole.USER)
                .status(AuthStatus.ACTIVE)
                .build();
    }
}
