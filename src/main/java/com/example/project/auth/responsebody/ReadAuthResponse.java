package com.example.project.auth.responsebody;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReadAuthResponse {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String loginId;
}
