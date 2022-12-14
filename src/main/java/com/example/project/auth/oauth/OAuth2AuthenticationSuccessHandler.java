package com.example.project.auth.oauth;

import com.example.project.auth.configuration.util.JwtTokenProvider;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
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
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthEntityRepository authEntityRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> kakao_account;
        Map<String, Object> kakao_profile;
        String email = null;
        String nickname = null;
        String profileImg = null;
        String ageGroup = null;
        String socialType = null;

        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());

        if (oAuth2User.getAttributes().containsKey("email")) {
            email = String.valueOf(oAuth2User.getAttributes().get("email"));
            nickname = String.valueOf(oAuth2User.getAttributes().get("nickname"));
            profileImg = String.valueOf(oAuth2User.getAttributes().get("profile_image"));
            ageGroup = String.valueOf(oAuth2User.getAttributes().get("age"));
            socialType = "naver";
            log.info("@@@@@@@@@@@@@" + email);
            log.info("#############" +nickname);
        }

        AuthEntity authEntity = authEntityRepository.findByEmail(email);
        String url;
        if (authEntityRepository.existsByEmail(email)) {
            String socialToken = jwtTokenProvider.createSocialToken(authEntity.getId());
            response.addHeader("Authorization", socialToken);
            url = sendExistInfoToRedirectUrI(socialToken, authEntity.getId());
        } else {
            if (socialType.equals("naver")) {
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                url = sendInfoToNaverRedirectUrl(email, nickname, profileImg, ageGroup, socialType);
            } else {
                System.out.println("nickName = " + nickname);
                System.out.println("socialType = " + socialType);
                System.out.println("profileImg = " + profileImg);
                System.out.println("ageGroup = " + ageGroup);
                url = sendInfoToRedirectUrl(email, nickname, profileImg, ageGroup, socialType);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String sendExistInfoToRedirectUrI(String token, Long userId) {
        return UriComponentsBuilder.fromUriString("https://cosmost-fe.vercel.app/"
                        + "/userId=" + userId + "/role=" + AuthRole.USER)
                .build().toUriString();
    }

    private String sendInfoToRedirectUrl(String email, String nickName, String profileImg, String ageGroup, String socialType) {
        String e = "/email=";
        String n = "/nickname=";
        String p = "/profileImg=";
        String a = "/ageGroup=";
        String s = "/socialType=";

        String encode = URLEncoder.encode(nickName, StandardCharsets.UTF_8);

        return UriComponentsBuilder.fromUriString("https://cosmost-fe.vercel.app/" + e + email + n + encode + p + profileImg + a + ageGroup + s + socialType)
                .build().toUriString();
    }

    private String sendInfoToNaverRedirectUrl(String email, String nickName, String profileImg, String ageGroup, String socialType) {
        String e = "/email=";
        String n = "/nickname=";
        String p = "/profileImg=";
        String a = "/ageGroup=";
        String s = "/socialType=";

        String encode = URLEncoder.encode(nickName, StandardCharsets.UTF_8);
        return UriComponentsBuilder.fromUriString("https://cosmost-fe.vercel.app/" + e + email
                        + n + encode + p + profileImg + a + ageGroup + s + socialType)
                .build().toUriString();
    }
}