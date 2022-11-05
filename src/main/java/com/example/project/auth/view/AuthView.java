package com.example.project.auth.view;

import com.example.project.auth.infrastructure.entity.AuthMarried;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthSns;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import com.example.project.auth.model.Auth;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Builder
@AllArgsConstructor
@ToString
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthView {
    private Long id;
    private String profileImgOriginName;
    private String profileImgSaveName;
    private String profileImgSaveUrl;
    private String nickname;

    // loginId, email, address, role, sns, status

    private String loginId;
    private String email;
    private String address;

    private AuthRole role;
    private AuthSns sns;
    private AuthStatus status;


    // front feedback
    private String loginPwd;

    private String ageGroup;

    private AuthMarried married;

    public AuthView(Auth auth) {
        this.id = auth.getId();
        this.profileImgOriginName = auth.getProfileImgOriginName();
        this.profileImgSaveName = auth.getProfileImgSaveName();
        this.profileImgSaveUrl = auth.getProfileImgSaveUrl();
        this.nickname = auth.getNickname();
        this.loginId = auth.getLoginId();
        this.email = auth.getEmail();
        this.address = auth.getAddress();
        this.role = auth.getRole();
        this.sns = auth.getSns();
        this.status = auth.getStatus();

        // front feedback
        this.loginPwd = auth.getLoginPwd();
        this.ageGroup = auth.getAgeGroup();
        this.married = auth.getMarried();
    }
}
