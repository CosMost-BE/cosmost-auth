package com.example.project.auth.service;

import com.example.project.auth.configuration.util.JwtTokenProvider;
import com.example.project.auth.exception.AuthIdNotFound;
import com.example.project.auth.exception.DuplicatedId;
import com.example.project.auth.exception.WithDrawalCheckNotFound;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthSns;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.DeleteAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthEntityRepository authEntityRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthEntityRepository authEntityRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.authEntityRepository = authEntityRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthEntity createAuth(CreateAuthRequest createAuthRequest) {

        return authEntityRepository.save(
                AuthEntity.builder()
                        .loginId(createAuthRequest.getLoginId())
                        .loginPwd(passwordEncoder.encode(createAuthRequest.getLoginPwd()))
                        .email(createAuthRequest.getEmail())
                        .role(AuthRole.USER)
                        .status(AuthStatus.ACTIVE)
                        .nickName(createAuthRequest.getNickName())
                        .address(createAuthRequest.getAddress())
                        .sns(createAuthRequest.getSns())
                        .married(createAuthRequest.getMarried())
                        .ageGroup(createAuthRequest.getAgeGroup())
                        .profileImgOriginName(createAuthRequest.getProfileImgOriginName())
                        .profileImgSaveName(createAuthRequest.getProfileImgSaveName())
                        .profileImgSaveUrl(createAuthRequest.getProfileImgSaveUrl())
                        .build());
    }

    @Override
    public boolean checkId(String loginId) { // 아이디 중복확인
//        return authEntityRepository.existsByLoginId(loginId);
//        checkId()
        boolean s = authEntityRepository.existsByLoginId(loginId);
        if (s == true) {
            throw new DuplicatedId();
        }
        return false;
    }

    @Override
    public String putAuth(UpdateAuthRequest updateAuthRequest) { // 로그인
        // optional
        Optional<AuthEntity> auth = authEntityRepository.findByLoginId(updateAuthRequest.getLoginId());

        // 회원가입했는지 비교, 넘겨받은 비밀번호와 암호화된 비밀번호 비교, 소셜 회원가입 여부 비교, 회원탈퇴 비교
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(auth != null && encoder.matches(updateAuthRequest.getLoginPwd(), auth.get().getLoginPwd()) &&
                auth.get().getSns().equals(AuthSns.NO) && auth.get().getStatus().equals(AuthStatus.ACTIVE)) {
            return jwtTokenProvider.createToken((auth.get().getId()), String.valueOf(auth.get().getRole()));
        }
        return null;
    }


    @Override
    @Transactional
    // 회원 탈퇴
    public Optional<AuthEntity> deleteAuth(Long id, DeleteAuthRequest deleteAuthRequest) {
//        String id = jwtTokenProvider.getUserPk(jwtTokenProvider.getToken(request));
//        Optional<AuthEntity> auth = authEntityRepository.findById(Long.valueOf(id));
//        if(auth.isPresent()) {
//            auth.get().setIsLeave(true);
//            return auth;
//        }
//        return null;
        Optional<AuthEntity> check = Optional.ofNullable(
                authEntityRepository.findById(id).orElseThrow(
                        WithDrawalCheckNotFound::new));

        if (check.isPresent()) {
            if (deleteAuthRequest.getAuthDropCheck().equals(true)) {
                authEntityRepository.deleteById(id);
            } else {
                throw new WithDrawalCheckNotFound();
            }
        } else {
            throw new AuthIdNotFound();
        }
        return null;
    }
}