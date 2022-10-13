package com.example.project.auth.model;

import com.example.project.auth.infrastructure.entity.AuthMarried;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthSns;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import lombok.*;

import java.sql.Timestamp;

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

    private AuthSns sns;

    private String nickName;

    private String address;

    private String ageGroup;

    private String birthdate;

    private AuthMarried married;

    private String profileImgOriginName;

    private String profileImgSaveName;

    private String profileImgSaveUrl;

    private AuthRole role;

    private AuthStatus status;
}
