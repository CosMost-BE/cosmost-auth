package com.example.project.auth.service;

import com.example.project.auth.configuration.util.JwtTokenProvider;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthSns;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.PutAuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthEntityRepository authEntityRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(AuthEntityRepository authEntityRepository, JwtTokenProvider jwtTokenProvider) {
        this.authEntityRepository = authEntityRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String createAuth(CreateAuthRequest createAuthRequest) {
        AuthEntity auth = signUpDtoEntity(createAuthRequest);
        authEntityRepository.save(auth);
        return String.valueOf(auth.getId());
    }

    @Override
    public String putAuth(PutAuthRequest putAuthRequest) { // 로그인
        AuthEntity auth = authEntityRepository.findByLoginId(putAuthRequest.getLoginId());

        // 회원가입했는지 비교, 넘겨받은 비밀번호와 암호화된 비밀번호 비교
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(auth != null && encoder.matches(putAuthRequest.getLoginPwd(), auth.getLoginPwd()) &&
                auth.getSns() == AuthSns.NO && auth.getStatus() == AuthStatus.SIGN_OUT && auth.getStatus() == AuthStatus.INACTIVE) {
            return jwtTokenProvider.createToken((auth.getId()), String.valueOf(auth.getRole()));
        }
        return null;
    }
}