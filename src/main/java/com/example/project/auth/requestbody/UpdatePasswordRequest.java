package com.example.project.auth.requestbody;

import com.example.project.auth.exception.UpdatePasswordFail;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "기존 비밀번호는 필수 입력 값입니다.")
    private String oldPwd;

    @NotBlank(message = "새 비밀번호는 필수 입력 값입니다.")
    private String newPwd;

    public AuthEntity toEntity(Long id, UpdatePasswordRequest updatePasswordRequest) {
        return AuthEntity.builder()
                .id(id)
                .loginPwd(updatePasswordRequest.getOldPwd())
                .loginPwd(updatePasswordRequest.getNewPwd())
                .build();
    }

}
