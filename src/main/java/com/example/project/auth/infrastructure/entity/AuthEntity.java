package com.example.project.auth.infrastructure.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Builder
@AllArgsConstructor
@Entity
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
    private String nickName;

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

}
