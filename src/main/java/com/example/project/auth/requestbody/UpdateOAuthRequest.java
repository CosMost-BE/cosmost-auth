package com.example.project.auth.requestbody;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthMarried;
import com.example.project.auth.infrastructure.entity.AuthSns;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdateOAuthRequest {

    private String nickname;

    private String email;

    private String ageGroup;

    private String address;

    private AuthMarried married;

    private AuthSns sns;

    public AuthEntity oauthDtoEntity(UpdateOAuthRequest updateOAuthRequest) {

        return AuthEntity.builder()
                .nickname(updateOAuthRequest.getNickname())
                .email(updateOAuthRequest.getEmail())
                .ageGroup(updateOAuthRequest.getAgeGroup())
                .address(updateOAuthRequest.getAddress())
                .married(updateOAuthRequest.getMarried())
                .sns(updateOAuthRequest.getSns())
                .build();
    }
}
