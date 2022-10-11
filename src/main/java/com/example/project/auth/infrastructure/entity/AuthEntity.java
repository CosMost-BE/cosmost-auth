package com.example.project.auth.infrastructure.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @NotBlank
    private String loginId;

    @NotBlank
    private String loginPwd;

    @NotBlank
    private String name;

    private String email;

    private Timestamp loginDate;

    private boolean sns;

    private String nickName;

    private String address;

    private String birthdate;

    private boolean married;

    private String profileImgOriginName;

    private String profileImgSaveName;

    private String profileImgSaveUrl;


    @Enumerated(EnumType.STRING)
    private AuthRole authRole;


    @Enumerated(EnumType.STRING)
    private AuthStatus authStatus;

}
