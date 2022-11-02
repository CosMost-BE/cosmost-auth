package com.example.project.auth.oauth;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final RedisService redisService;
    private final AuthEntityRepository authEntityRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        log.info(String.valueOf("@@@@@@@@@@@@@@@@@@" + oAuth2User.getAttributes()));
        log.info(String.valueOf("##################" + oAuth2User.getAttributes().keySet()));

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("$$$$$$$$$$$$$$$$$$$$$" + registrationId);

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // naver
        String socialType = registrationId;
        AuthEntity authEntity = createSocialUser(attributes, socialType);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + authEntity.getRole())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    public AuthEntity createSocialUser(OAuthAttributes attributes, String socialType) {
        AuthEntity authEntity;

        if (authEntityRepository.existsByEmail(attributes.getEmail())) {
            authEntity = authEntityRepository.findByEmail(attributes.getEmail());
            return authEntity;
        } else
            return redisService.createTemporalOAuthUser(attributes.getEmail(),
                    attributes.getNickname(),
                    attributes,
                    socialType);
    }
}
