package com.example.project.auth.view;

import com.example.project.auth.model.Auth;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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

    public AuthView(Auth auth) {
        this.id = auth.getId();
        this.profileImgOriginName = auth.getProfileImgOriginName();
        this.profileImgSaveName = auth.getProfileImgSaveName();
        this.profileImgSaveUrl = auth.getProfileImgSaveUrl();
        this.nickname = auth.getNickname();
    }
}