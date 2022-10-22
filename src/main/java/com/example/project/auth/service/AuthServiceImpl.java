package com.example.project.auth.service;

import com.example.project.auth.configuration.util.JwtTokenProvider;
import com.example.project.auth.exception.*;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthSns;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.model.Auth;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.requestbody.UpdateLoginRequest;
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

    @Override // 회원가입
    public AuthEntity createAuth(CreateAuthRequest createAuthRequest) {

        return authEntityRepository.save(
                AuthEntity.builder()
                        .loginId(createAuthRequest.getLoginId())
                        .loginPwd(passwordEncoder.encode(createAuthRequest.getLoginPwd()))
                        .email(createAuthRequest.getEmail())
                        .role(AuthRole.USER)
                        .status(AuthStatus.ACTIVE)
                        .nickname(createAuthRequest.getNickname())
                        .address(createAuthRequest.getAddress())
                        .sns(createAuthRequest.getSns())
                        .married(createAuthRequest.getMarried())
                        .ageGroup(createAuthRequest.getAgeGroup())
                        .profileImgOriginName(createAuthRequest.getProfileImgOriginName())
                        .profileImgSaveName(createAuthRequest.getProfileImgSaveName())
                        .profileImgSaveUrl(createAuthRequest.getProfileImgSaveUrl())
                        .build());
    }

    @Override // 아이디 중복확인
    public Boolean checkId(HttpServletRequest request) throws DuplicatedIdException {
        String header = jwtTokenProvider.getHeader(request);
        log.info(String.valueOf(header));
        if (header.equals(authEntityRepository.existsByLoginId(header))) {
            throw new DuplicatedIdException();
        }
        return true;
    }


    @Override // 중복 닉네임 확인
    public Boolean checkNickname(HttpServletRequest request) throws DuplicatedNickname { // 닉네임 중복확인
        String header = jwtTokenProvider.getHeader(request);
        log.info(String.valueOf(header));
        if (header.equals(authEntityRepository.existsByNickname(header))) {
            throw new DuplicatedNickname();
        }
        return true;
    }


    @Override // 로그인
    public String updateLoginAuth(UpdateLoginRequest updateLoginRequest) {
        // optional
        Optional<AuthEntity> auth = authEntityRepository.findByLoginId(updateLoginRequest.getLoginId());

        // 회원가입했는지 비교, 넘겨받은 비밀번호와 암호화된 비밀번호 비교, 소셜 회원가입 여부 비교, 회원탈퇴 비교
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(auth != null && encoder.matches(updateLoginRequest.getLoginPwd(), auth.get().getLoginPwd()) &&
                auth.get().getSns().equals(AuthSns.NO) && auth.get().getStatus().equals(AuthStatus.ACTIVE)) {
            return jwtTokenProvider.createToken((auth.get().getId()), String.valueOf(auth.get().getRole()));
        }
        return null;
    }

    @Override // 회원 탈퇴
    @Transactional
    public void deleteAuthInfo(HttpServletRequest request, UpdateAuthRequest updateAuthRequest) throws WithdrawalCheckNotFound {
        String authId = jwtTokenProvider.getUserPk(jwtTokenProvider.getToken(request));
        Optional<AuthEntity> auth = authEntityRepository.findById(Long.valueOf(authId));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String securePwd = encoder.encode(updateAuthRequest.getLoginPwd());

        // 회원가입시 비밀번호
        String oldPwd = auth.get().getLoginPwd();
        // 회원탈퇴 시 비밀번호
        String newPwd = updateAuthRequest.getLoginPwd();

        if (auth.isPresent() && encoder.matches(newPwd, oldPwd)) {
            authEntityRepository.save(updateAuthRequest.infoDtoEntity(auth.get().getId(), updateAuthRequest, securePwd));
        }
    }

    @Override // 회원정보 수정
    @Transactional
    public void updateAuthInfo(UpdateAuthRequest updateAuthRequest, HttpServletRequest request) throws UpdateAuthFail {
        String token = jwtTokenProvider.getToken(request);
        Long id = Long.valueOf(jwtTokenProvider.getUserPk(token));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 비밀번호 암호화하여 다시 user 객체에 저장
        String securePwd = encoder.encode(updateAuthRequest.getLoginPwd());

        Optional<AuthEntity> authInfo = Optional.ofNullable(
                authEntityRepository.findById(id).orElseThrow(
                        UpdateAuthFail::new));

        if (authInfo.isPresent()) {
            AuthEntity authEntity = updateAuthRequest.infoDtoEntity(authInfo.get().getId(), updateAuthRequest, securePwd);
            authEntityRepository.save(authEntity);
        }
    }

    // 회원정보 조회
    @Override
    @Transactional
    public Auth readAuth(HttpServletRequest request) throws ReadAuthFail{
        String token = jwtTokenProvider.getToken(request);
        Long id = Long.valueOf(jwtTokenProvider.getUserPk(token));

        Optional<AuthEntity> authEntityList = authEntityRepository.findById(id);
        authEntityList.get().getLoginId();

        return Auth.builder()
                .id(authEntityList.get().getId())
                .loginId(authEntityList.get().getLoginId())
                .loginPwd(authEntityList.get().getLoginPwd())
                .email(authEntityList.get().getEmail())
                .sns(authEntityList.get().getSns())
                .nickname(authEntityList.get().getNickname())
                .address(authEntityList.get().getAddress())
                .ageGroup(authEntityList.get().getAgeGroup())
                .married(authEntityList.get().getMarried())
                .profileImgOriginName(authEntityList.get().getProfileImgOriginName())
                .profileImgSaveName(authEntityList.get().getProfileImgSaveName())
                .profileImgSaveUrl(authEntityList.get().getProfileImgSaveUrl())
                .role(authEntityList.get().getRole())
                .status(authEntityList.get().getStatus())
                .build();
    }
}