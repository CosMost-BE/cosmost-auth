package com.example.project.auth.model;

import com.example.project.auth.infrastructure.entity.AuthMarried;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthSns;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class Auth {

    private String id;

    private String loginId;

    private String loginPwd;

    private String name;

    private String email;

    private Timestamp loginDate;

    @Enumerated(EnumType.STRING)
    private AuthSns authSns;

    private String nickName;

    private String address;

    private String birthdate;

    @Enumerated(EnumType.STRING)
    private AuthMarried authMarried;

    private String profileImgOriginName;

    private String profileImgSaveName;

    private String profileImgSaveUrl;

    @Enumerated(EnumType.STRING)
    private AuthRole authRole;

    @Enumerated(EnumType.STRING)
    private AuthStatus authStatus;
}
