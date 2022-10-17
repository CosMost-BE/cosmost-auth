package com.example.project.auth.requestbody;

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
public class UpdateAuthRequest {

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String loginId;

    private String loginPwd;

    private String ageGroup;

    private AuthMarried married;

    private String email;

    private String address;

    public AuthEntity infoDtoEntity(Long id, AuthEntity authEntity) {
        return AuthEntity.builder()
                .id(id)
                .loginId(authEntity.getLoginId())
                .loginPwd(authEntity.getLoginPwd())
                .email(authEntity.getEmail())
                .married(authEntity.getMarried())
                .nickname(authEntity.getNickname())
                .address(authEntity.getAddress())
                .ageGroup(authEntity.getAgeGroup())
                .build();
    }
}
