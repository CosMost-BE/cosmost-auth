package com.example.project.auth.requestbody;

import com.example.project.auth.infrastructure.entity.*;
import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateAuthRequest {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String loginId;

//    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String loginPwd;

    private String email;

    private AuthMarried married;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    private String address;

    private String ageGroup;

    private AuthSns sns;

    private String profileImgOriginName;

    private String profileImgSaveName;

    private String profileImgSaveUrl;

    private AuthRole role;

    private AuthStatus status;
}
