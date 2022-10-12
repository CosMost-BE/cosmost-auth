package com.example.project.auth.requestbody;

import com.example.project.auth.infrastructure.entity.*;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
public class CreateAuthRequest {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String loginPwd;

    private String email;

    private AuthMarried married;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickName;

    private String address;

    private String ageGroup;

    private AuthSns sns;

    private String profileImgOriginName;

    private String profileImgSaveName;

    private String profileImgSaveUrl;

    private AuthRole role;

    private AuthStatus status;

    public AuthEntity signUpDtoEntity(CreateAuthRequest createAuthRequest) {
        return AuthEntity.builder()
                .loginId(createAuthRequest.getLoginId())
                .loginPwd(createAuthRequest.getLoginPwd())
                .email(createAuthRequest.getEmail())
                .role(AuthRole.USER)
                .status(AuthStatus.ACTIVE)
                .nickName(createAuthRequest.getNickName())
                .address(createAuthRequest.getAddress())
                .sns(createAuthRequest.getSns())
                .married(createAuthRequest.getMarried())
                .ageGroup(createAuthRequest.getAgeGroup())
                .profileImgOriginName(createAuthRequest.getProfileImgOriginName())
                .profileImgSaveName(createAuthRequest.getProfileImgSaveName())
                .profileImgSaveUrl(createAuthRequest.getProfileImgSaveUrl())
                .build();
    }

}
