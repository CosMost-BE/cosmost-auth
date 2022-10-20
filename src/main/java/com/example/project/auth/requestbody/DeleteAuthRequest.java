package com.example.project.auth.requestbody;


import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteAuthRequest {
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String loginPwd;

    public AuthEntity withdrawalDtoEntity(DeleteAuthRequest deleteAuthRequest) {
        return AuthEntity.builder()
                .loginPwd(deleteAuthRequest.getLoginPwd())
                .build();
    }
}
