package com.example.project.auth.requestbody;

import com.example.project.auth.infrastructure.entity.AuthMarried;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthSns;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateAuthRequest {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String loginPwd;

    private String email;

    private Timestamp loginDate;

    private AuthMarried authMarried;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickName;

    private String address;

    private String birthdate;

    private AuthSns authSns;

    private String profileImgOriginName;

    private String type;

    private String profileImgSaveName;

    private String profileImgSaveUrl;

    @NotBlank(message = "권한은 필수입니다.")
    private AuthRole authRole;

    private AuthStatus authStatus;

}
