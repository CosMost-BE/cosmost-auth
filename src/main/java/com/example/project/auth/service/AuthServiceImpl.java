package com.example.project.auth.service;

import com.example.project.auth.configuration.util.JwtTokenProvider;
import com.example.project.auth.exception.DuplicatedId;
import com.example.project.auth.exception.WithdrawalCheckNotFound;
import com.example.project.auth.exception.UpdateAuthFail;
import com.example.project.auth.exception.DuplicatedIdException;
import com.example.project.auth.exception.DuplicatedNickname;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.AuthRole;
import com.example.project.auth.infrastructure.entity.AuthSns;
import com.example.project.auth.infrastructure.entity.AuthStatus;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.DeleteAuthRequest;
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

    @Override
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


    public Boolean checkNickname(HttpServletRequest request) throws DuplicatedNickname { // 닉네임 중복확인
        String header = jwtTokenProvider.getHeader(request);
        if (header.equals(authEntityRepository.existsByNickname(header))) {
            throw new DuplicatedNickname();
        }
        return true;
    }


    @Override
    public String updateLoginAuth(UpdateLoginRequest updateAuthRequest) { // 로그인
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
    public AuthEntity putUserAuth(HttpServletRequest request, DeleteAuthRequest deleteAuthRequest) throws WithdrawalCheckNotFound {
        String token = jwtTokenProvider.getToken(request);
        String auth = jwtTokenProvider.getUserPk(token);

        Optional<AuthEntity> auth2 = authEntityRepository.findById(Long.valueOf(auth));

        String oldPwd = auth2.get().getLoginPwd();

        // 회원탈퇴 시 비밀번호
        String newPwd = deleteAuthRequest.getLoginPwd();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(newPwd, oldPwd)) {
            return authEntityRepository.save(AuthEntity.builder()
                            .loginId(auth2.get().getLoginId())
                            .status(AuthStatus.WITHDRAWAL)
                            .loginPwd(auth2.get().getLoginPwd())
                            .email(auth2.get().getEmail())
                            .married(auth2.get().getMarried())
                            .nickname(auth2.get().getNickname())
                            .address(auth2.get().getAddress())
                            .ageGroup(auth2.get().getAgeGroup())
                            .sns(auth2.get().getSns())
                            .profileImgOriginName(auth2.get().getProfileImgOriginName())
                            .profileImgSaveName(auth2.get().getProfileImgSaveName())
                            .profileImgSaveUrl(auth2.get().getProfileImgSaveUrl())
                            .role(auth2.get().getRole())
                    .build());

        } else {
            throw new WithdrawalCheckNotFound();
        }
    }
}

    @Override // 회원정보 수정
    public AuthEntity updateAuthInfo(UpdateAuthRequest updateAuthRequest, HttpServletRequest request) throws UpdateAuthFail {
        String token = jwtTokenProvider.getToken(request);
        Long id = Long.valueOf(jwtTokenProvider.getUserPk(token));
        log.info(String.valueOf(id));
        try {
            AuthEntity auth = authEntityRepository.findById(id).orElseThrow(() ->
                    new UpdateAuthFail()
            );
            authEntityRepository.save(updateAuthRequest.infoDtoEntity(auth.getId(), updateAuthRequest));
        } catch (Exception exception) {
            throw new UpdateAuthFail();
        }
        return null;
    }
}
