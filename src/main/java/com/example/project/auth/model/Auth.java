package com.example.project.auth.model;

import com.example.project.auth.infrastructure.entity.*;
import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class Auth {

    private Long id;
    private String loginId;
    private String loginPwd;
    private String email;
    private AuthSns sns;
    private String nickname;
    private String address;
    private String ageGroup;
    private AuthMarried married;
    private String profileImgOriginName;
    private String profileImgSaveName;
    private String profileImgSaveUrl;
    private AuthRole role;
    private AuthStatus status;



    public Auth(AuthEntity entity) {
        this.id = entity.getId();
        this.loginId = entity.getLoginId();
        this.loginPwd = entity.getLoginPwd();
        this.email = entity.getEmail();
        this.sns = entity.getSns();
        this.nickname = entity.getNickname();
        this.address = entity.getAddress();
        this.ageGroup = entity.getAgeGroup();
        this.married = entity.getMarried();
        this.profileImgOriginName = entity.getProfileImgOriginName();
        this.profileImgSaveName = entity.getProfileImgSaveName();
        this.profileImgSaveUrl = entity.getProfileImgSaveUrl();
        this.role = entity.getRole();
        this.status = entity.getStatus();
    }
}