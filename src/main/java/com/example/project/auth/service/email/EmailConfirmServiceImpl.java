package com.example.project.auth.service.email;

import com.example.project.auth.configuration.util.JwtTokenProvider;
import com.example.project.auth.exception.EmailCodeException;
import com.example.project.auth.exception.UpdateAuthFail;
import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.infrastructure.repository.UserConfirmRepository;
import com.example.project.auth.requestbody.UpdateEmailRequest;
import com.example.project.auth.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@PropertySource(value = "classpath:/application-email.yml")
@Slf4j
@RequiredArgsConstructor
@Service
@Component
public class EmailConfirmServiceImpl implements EmailConfirmService {

    @Autowired
    JavaMailSender emailSender;
    private final Environment env;
    private final UserConfirmRepository userConfirmRepository;
    private final AuthEntityRepository authEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;


    // 회원가입 시 인증코드 검증
    @Override
    public boolean userEmailConfirm(String code, String email) {
        if (redisService.hasKey(email) && redisService.getEmailCertification(email).equals(code)) {
            UserConfirmEntity userConfirmEntity = (UserConfirmEntity) userConfirmRepository.findByEmail(email);
            redisService.removeEmailCertification(email);
        } else {
            return false;
        }
        return true;
    }


    // 아이디 찾기 시 인증코드 검증
    @Override
    public String userIdReissue(String code, String email) throws EmailCodeException {
        if (redisService.hasKey(email) && redisService.getEmailCertification(email).equals(code)) {
            AuthEntity authEntity = authEntityRepository.findByEmail(email);
            redisService.removeEmailCertification(email);
            return authEntity.getLoginId();
        } else {
            throw new EmailCodeException();
        }
    }


    // 비밀번호 찾기 시 이메일 인증코드 검증
    @Override
    public Long userPasswordReissue(String code, String email) throws EmailCodeException {
        if (redisService.hasKey(email) && redisService.getEmailCertification(email).equals(code)) {
            log.info("????????????????????????????");
            AuthEntity authEntity = authEntityRepository.findByEmail(email);
            redisService.removeEmailCertification(email);
            return authEntity.getId();
        } else {
            log.info("############");
            throw new EmailCodeException();
        }
    }

    // 비밀번호 찾기 시 새 비밀번호 입력 할 때
    @Override
    public AuthEntity userNewpasswordReissue(Long id, String newpwd) {
        log.info("@@@@@@@@@@@" + String.valueOf(id), newpwd);
        AuthEntity authEntity = authEntityRepository.findById(id).get();
        log.info("############" + authEntity.getId());
        log.info("############" + authEntity.getLoginPwd());



        return authEntityRepository.save(
                AuthEntity.builder()
                        .id(id)
                        .loginId(authEntity.getLoginId())
                        .loginPwd(bCryptPasswordEncoder.encode(newpwd))
                        .email(authEntity.getEmail())
                        .role(AuthRole.USER)
                        .status(AuthStatus.ACTIVE)
                        .nickname(authEntity.getNickname())
                        .address(authEntity.getAddress())
                        .sns(authEntity.getSns())
                        .married(authEntity.getMarried())
                        .ageGroup(authEntity.getAgeGroup())
                        .profileImgOriginName(authEntity.getProfileImgOriginName())
                        .profileImgSaveName(authEntity.getProfileImgSaveName())
                        .profileImgSaveUrl(authEntity.getProfileImgSaveUrl())
                        .build());
    }


    // 이메일 변경 시 인증코드 검증
   @Override
    public void userNewEmailReissue(String code, String email, HttpServletRequest request, UpdateEmailRequest updateEmailRequest) {

        String token = jwtTokenProvider.getToken(request);
        Long id = Long.valueOf(jwtTokenProvider.getUserPk(token));

        BCryptPasswordEncoder bCryptPasswordEncoder1 = new BCryptPasswordEncoder();

        Optional<AuthEntity> authInfo = Optional.ofNullable(
               authEntityRepository.findById(id).orElseThrow(
                       UpdateAuthFail::new));

        String securePwd = bCryptPasswordEncoder1.encode(authInfo.get().getLoginPwd());

        authEntityRepository.save(updateEmailRequest.infoEmailDtoEntity(id, updateEmailRequest, email, securePwd));
    }
}