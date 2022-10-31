package com.example.project.auth.configuration.utils.oauth;

import com.example.project.auth.configuration.utils.JwtTokenProvider;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.infrastructure.repository.UserConfirmRepository;
import com.example.project.auth.model.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserConfirmRepository userConfirmRepository;

    private final AuthEntityRepository authEntityRepository;

    @Autowired
    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider, UserConfirmRepository userConfirmRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userConfirmRepository = userConfirmRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> kakao_account;
        String email;

        if (oAuth2User.getAttributes().containsKey("kakao_account")) {
            kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            email = String.valueOf(kakao_account.get("email"));
        } else {
            email = String.valueOf(oAuth2User.getAttributes().get("email"));
        }

        Optional<Auth> auth = userConfirmRepository.findByEmail(email);

        String jwt = jwtTokenProvider.createToken(user.get().getUserId(), String.valueOf(Role.USER));

        String url = makeRedirectUrl(jwt);

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, url);

    }

    private String makeRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString("http://13.209.26.150:9000/comm-users/social/" + token)
                .build().toUriString();
    }
}