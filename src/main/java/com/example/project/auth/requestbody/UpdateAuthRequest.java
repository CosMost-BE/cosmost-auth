package com.example.project.auth.requestbody;

import com.example.project.auth.infrastructure.entity.*;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
public class UpdateAuthRequest {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String loginId;

    private String loginPwd;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    private String email;

    private String address;

    private AuthRole role;

    private AuthSns sns;

    private AuthStatus status;

    private String ageGroup;

    private AuthMarried married;

    private String profileImgOriginName;

    private String profileImgSaveName;

    private String profileImgSaveUrl;

    private String type;

    public AuthEntity infoDtoEntity(Long id, UpdateAuthRequest updateAuthRequest) {
        return AuthEntity.builder()
                .id(id)
                .loginId(updateAuthRequest.getLoginId())
                .loginPwd(updateAuthRequest.getLoginPwd())
                .nickname(updateAuthRequest.getNickname())
                .email(updateAuthRequest.getEmail())
                .address(updateAuthRequest.getAddress())
                .role(updateAuthRequest.getRole())
                .sns(updateAuthRequest.getSns())
                .status(updateAuthRequest.getStatus())
                .ageGroup(updateAuthRequest.getAgeGroup())
                .married(updateAuthRequest.getMarried())
                .profileImgOriginName(updateAuthRequest.getProfileImgOriginName())
                .profileImgSaveName(updateAuthRequest.getProfileImgSaveName())
                .profileImgSaveUrl(updateAuthRequest.getProfileImgSaveUrl())
                .build();
    }
}
