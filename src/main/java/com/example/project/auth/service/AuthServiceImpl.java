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

import javax.swing.text.html.Option;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthEntityRepository authEntityRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(AuthEntityRepository authEntityRepository, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authEntityRepository = authEntityRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String createAuth(CreateAuthRequest createAuthRequest) { // 회원가입

        createAuthRequest.setLoginPwd(bCryptPasswordEncoder.encode(createAuthRequest.getLoginPwd()));
        AuthEntity auth = createAuthRequest.signUpDtoEntity(createAuthRequest);
        authEntityRepository.save(auth);
        return String.valueOf(auth.getId());

    }

    @Override
    public String putAuth(PutAuthRequest putAuthRequest) { // 로그인
        AuthEntity auth = authEntityRepository.findByLoginId(putAuthRequest.getLoginId());

        // 회원가입했는지 비교, 넘겨받은 비밀번호와 암호화된 비밀번호 비교, 소셜 회원가입 여부 비교, 회원탈퇴 비교
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(auth != null && encoder.matches(putAuthRequest.getLoginPwd(), auth.getLoginPwd()) &&
                auth.getSns() == AuthSns.NO && auth.getStatus() == AuthStatus.ACTIVE) {
            return jwtTokenProvider.createToken((auth.getId()), String.valueOf(auth.getRole()));
        }
        return null;
    }
}