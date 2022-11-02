package com.example.project.auth.requestbody;

import com.example.project.auth.infrastructure.entity.*;
import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String loginId;

    @NotBlank(message = "기존 비밀번호는 필수 입력 값입니다.")
    private String oldPwd;

    @NotBlank(message = "새 비밀번호는 필수 입력 값입니다.")
    private String newPwd;

    private String email;

    private String address;

    private AuthRole role;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    private AuthSns sns;

    private AuthStatus status;

    private String ageGroup;

    private AuthMarried married;

    private String profileImgOriginName;

    private String profileImgSaveName;

    private String profileImgSaveUrl;

    private String type;

    public AuthEntity toEntity(Long id, UpdatePasswordRequest updatePasswordRequest, String newPwd) {
        return AuthEntity.builder()
                .id(id)
                .loginId(updatePasswordRequest.getLoginId())
                .loginPwd(newPwd)
                .nickname(updatePasswordRequest.getNickname())
                .email(updatePasswordRequest.getEmail())
                .address(updatePasswordRequest.getAddress())
                .role(updatePasswordRequest.getRole())
                .sns(updatePasswordRequest.getSns())
                .status(updatePasswordRequest.getStatus())
                .ageGroup(updatePasswordRequest.getAgeGroup())
                .married(updatePasswordRequest.getMarried())
                .profileImgOriginName(updatePasswordRequest.getProfileImgOriginName())
                .profileImgSaveName(updatePasswordRequest.getProfileImgSaveName())
                .profileImgSaveUrl(updatePasswordRequest.getProfileImgSaveUrl())
                .build();
    }
}
