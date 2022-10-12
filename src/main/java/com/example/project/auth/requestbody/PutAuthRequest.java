package com.example.project.auth.requestbody;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PutAuthRequest {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String loginPwd;

    public AuthEntity loginDtoEntity(PutAuthRequest putAuthRequest) {
        return AuthEntity.builder()
                .loginId(putAuthRequest.getLoginId())
                .loginPwd(putAuthRequest.getLoginPwd())
                .build();
    }
}