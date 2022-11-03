package com.example.project.auth.service.email;

import com.example.project.auth.exception.EmailCodeException;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.UserConfirmEntity;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.infrastructure.repository.UserConfirmRepository;
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

import javax.swing.text.html.Option;
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

    @Override
    public boolean userEmailConfirm(String code, String email) {
        if (redisService.hasKey(email) && redisService.getEmailCertification(email).equals(code)) {
            UserConfirmEntity userConfirmEntity = userConfirmRepository.findByEmail(email);
            redisService.removeEmailCertification(email);
        } else {
            return false;
        }
        return true;
    }


    @Override
    public String userIdReissue(String code, String email) throws EmailCodeException {
        if (redisService.hasKey(email) && redisService.getEmailCertification(email).equals(code)) {
            UserConfirmEntity userConfirmEntity = userConfirmRepository.findByEmail(email);
            AuthEntity authEntity = authEntityRepository.findByEmail(email);
            authEntity.getLoginId();
            redisService.removeEmailCertification(email);
        } else {
            return EmailCodeException;
        }
        return authEntityRepository.findByEmail(email).getLoginId();
    }

    @Override
    public String userPasswordReissue(String code, String email) throws Exception {
        if (redisService.hasKey(email) && redisService.getEmailCertification(email).equals(code)) {
            UserConfirmEntity userConfirmEntity = userConfirmRepository.findByEmail(email);
            redisService.removeEmailCertification(email);
        } else {
            return EmailCodeException;
        }
        return authEntityRepository.findByEmail(email).getLoginPwd();
    }
}