package com.example.project.auth.view;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthView {
    private final Long id;
    private final String loginId;
    private final String loginPwd;
    public AuthView(AuthEntity auth) {
        this.id = auth.getId();
        this.loginId = auth.getLoginId();
        this.loginPwd = auth.getLoginPwd();
    }
}