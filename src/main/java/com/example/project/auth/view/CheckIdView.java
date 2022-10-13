package com.example.project.auth.view;


import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckIdView {

    private final String loginId;

    public CheckIdView(AuthEntity auth) {
        this.loginId = auth.getLoginId();
    }
}
