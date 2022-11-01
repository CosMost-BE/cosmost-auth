package com.example.project.auth.infrastructure.entity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "auth")
public class AuthEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String loginId;

    @NotNull
    private String loginPwd;

    private String email;
    @NotNull
    private String nickname;

    private String address;

    private String ageGroup;

    @Enumerated(EnumType.STRING)
    private AuthMarried married;

    @Enumerated(EnumType.STRING)
    private AuthSns sns;

    private String profileImgOriginName;
    private String profileImgSaveName;
    private String profileImgSaveUrl;

    @Enumerated(EnumType.STRING)
    private AuthRole role;

    @Enumerated(EnumType.STRING)
    private AuthStatus status;

    @Builder
    public AuthEntity(Long id, String loginId, String loginPwd, String email, String nickname, String address,
                      String ageGroup, AuthMarried married, AuthSns sns, String profileImgOriginName, String profileImgSaveName, String profileImgSaveUrl, AuthRole role, AuthStatus status) {
        this.id = id;
        this.loginId = loginId;
        this.loginPwd = loginPwd;
        this.email = email;
        this.nickname = nickname;
        this.address = address;
        this.ageGroup = ageGroup;
        this.married = married;
        this.sns = sns;
        this.profileImgOriginName = profileImgOriginName;
        this.profileImgSaveName = profileImgSaveName;
        this.profileImgSaveUrl = profileImgSaveUrl;
        this.role = role;
        this.status = status;
    }
}
