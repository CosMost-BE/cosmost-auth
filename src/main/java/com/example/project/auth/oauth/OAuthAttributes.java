package com.example.project.auth.oauth;

import com.example.project.auth.infrastructure.entity.AuthMarried;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthSns;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Slf4j
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;

    private String email;
    private String nickname;
    private String profileImg;
    private String ageGroup;


    private String socialType;
    private String address;
    private AuthMarried married;
    private AuthSns sns;

    @Enumerated(EnumType.STRING)
    private AuthRole role;

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return null;
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        log.info("@@@@@@@@@@@@" + String.valueOf(response));
        return OAuthAttributes.builder()
                .email(String.valueOf(response.get("email")))
                .nickname(String.valueOf(response.get("nickname")))
                .profileImg(String.valueOf(response.get("profile_image")))
                .ageGroup(String.valueOf(response.get("age")))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
}