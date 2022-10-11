package com.example.project.auth.infrastructure.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "auth")
public class AuthEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String loginId;

    @NotNull
    private String loginPwd;

    @NotNull
    private String name;

    private String email;

    private Timestamp loginDate;

    private String nickName;

    private String address;

    private String birthdate;

    @Enumerated(EnumType.STRING)
    private AuthMarried authMarried;

    @Enumerated(EnumType.STRING)
    private AuthSns authSns;

    private String profileImgOriginName;

    private String profileImgSaveName;

    private String profileImgSaveUrl;

    @Enumerated(EnumType.STRING)
    private AuthRole authRole;

    @Enumerated(EnumType.STRING)
    private AuthStatus authStatus;

}
