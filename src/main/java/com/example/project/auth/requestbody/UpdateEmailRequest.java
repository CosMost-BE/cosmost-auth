package com.example.project.auth.requestbody;

import com.example.project.auth.configuration.util.JwtTokenProvider;
import com.example.project.auth.infrastructure.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateEmailRequest {

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

    public AuthEntity infoEmailDtoEntity(Long id, UpdateEmailRequest updateEmailRequest, String email, String securePwd) {
        return AuthEntity.builder()
                .id(id)
                .loginPwd(securePwd)
                .loginId(updateEmailRequest.getLoginId())
                .nickname(updateEmailRequest.getNickname())
                .email(email)
                .address(updateEmailRequest.getAddress())
                .role(updateEmailRequest.getRole())
                .sns(updateEmailRequest.getSns())
                .status(updateEmailRequest.getStatus())
                .ageGroup(updateEmailRequest.getAgeGroup())
                .married(updateEmailRequest.getMarried())
                .profileImgOriginName(updateEmailRequest.getProfileImgOriginName())
                .profileImgSaveName(updateEmailRequest.getProfileImgSaveName())
                .profileImgSaveUrl(updateEmailRequest.getProfileImgSaveUrl())
                .build();
    }
}