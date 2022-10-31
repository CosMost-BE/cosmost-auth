package com.example.project.auth.oauth;

import com.example.project.auth.configuration.utils.JwtTokenProvider;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.UserConfirmEntity;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.infrastructure.repository.UserConfirmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserConfirmRepository userConfirmRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthEntityRepository authEntityRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> kakao_account;
        Map<String, Object> kakao_profile;
        String email;
        String socialType;
        String nickname;
        String mobile = null;

        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());
        if (oAuth2User.getAttributes().containsKey("kakao_account")) {
            socialType = "kakao";
            kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            kakao_profile = (Map<String, Object>) kakao_account.get("profile");
            email = String.valueOf(kakao_account.get("email"));
            nickname = String.valueOf(kakao_profile.get("nickname").toString());
        } else if (oAuth2User.getAttributes().containsKey("mobile")) {
            email = String.valueOf(oAuth2User.getAttributes().get("email"));
            nickname = String.valueOf(oAuth2User.getAttributes().get("nickname"));
            mobile = String.valueOf(oAuth2User.getAttributes().get("mobile"));
            socialType = "naver";
            log.info("@@@@@@@@@@@@@" + email);
            log.info("#############" +nickname);
            log.info("%%%%%%%%%%%%%" + mobile);
        } else {
            email = String.valueOf(oAuth2User.getAttributes().get("email"));
            nickname = String.valueOf(oAuth2User.getAttributes().get("name"));
            socialType = "google";
        }

        AuthEntity authEntity = authEntityRepository.findByEmail(email);
        String url;
        if (authEntityRepository.existsByEmail(email)) {
            String socialToken = jwtTokenProvider.createSocialToken(authEntity.getId());
            url = sendExistInfoToRedirectUrI(socialToken, authEntity.getId());
            response.addHeader("Authorization", socialToken);
        } else {
            if (socialType.equals("naver")) {
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                url = sendInfoToNaverRedirectUrl(email, nickname, socialType, mobile);
            } else {
                System.out.println("nickName = " + nickname);
                System.out.println("socialType = " + socialType);
                url = sendInfoToRedirectUrl(email, nickname, socialType);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String sendExistInfoToRedirectUrI(String token, Long userId) {
        return UriComponentsBuilder.fromUriString("https://v1/auth-service/api/v1/oauth/social"
                        + "/userId=" + userId + "/role=" + AuthRole.USER + "/token=" + token)
                .build().toUriString();
    }

    private String sendInfoToRedirectUrl(String email, String nickName, String socialType) {
        String e = "/email=";
        String n = "/nickname=";
        String s = "/socialType=";
        String m = "/socialType=";
        String encode = URLEncoder.encode(nickName, StandardCharsets.UTF_8);

        return UriComponentsBuilder.fromUriString("https://v1/login/oauth2/code/social" + e + email + n + encode + s + socialType)
                .build().toUriString();
    }

    private String sendInfoToNaverRedirectUrl(String email, String nickName, String socialType, String mobile) {
        String e = "/email=";
        String n = "/nickname=";
        String s = "/socialType=";
        String m = "/mobile=";

        String encode = URLEncoder.encode(nickName, StandardCharsets.UTF_8);

        String num = mobile.replaceAll("-", "");

        return UriComponentsBuilder.fromUriString("http://localhost:9001/login/oauth2/code/social" + e + email
                        + n + encode + s + socialType + m + num)
                .build().toUriString();
    }
}