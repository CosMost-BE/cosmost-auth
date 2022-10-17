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

    public AuthEntity infoDtoEntity(AuthEntity updateAuthRequest) {
        return AuthEntity.builder()
                .loginId(updateAuthRequest.getLoginId())
                .loginPwd(updateAuthRequest.getLoginPwd())
                .email(updateAuthRequest.getEmail())
                .married(updateAuthRequest.getMarried())
                .nickName(updateAuthRequest.getAddress())
                .ageGroup(updateAuthRequest.getAgeGroup())
                .build();
    }
}
