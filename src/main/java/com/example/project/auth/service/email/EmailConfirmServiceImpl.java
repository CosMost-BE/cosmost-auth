package com.example.project.auth.service.email;

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
    private final EmailConfirmServiceImpl emailServiceImpl;


    @Override
    public boolean userEmailConfirm(String code, String email) {
        UserConfirmEntity userConfirmEntity = userConfirmRepository.findByEmail(email);
        if (redisService.hasKey(email) && redisService.getSmsCertification(email).equals(code)) {
            redisService.removeSmsCertification(email);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean userIdReissue(String code, String email) {
//        UserConfirmEntity userConfirmEntity = userConfirmRepository.findByEmail(email);
//        if (userConfirmEntity == null) throw new UsernameNotFoundException(email);
//        if (userConfirmEntity.getConfirmKey().equals(code)) {
//            String ePw = emailServiceImpl.sendReissuePassword(email);
//            UserConfirmEntity userEntity = userConfirmRepository.findByEmail(email);
////            userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(ePw));
////            authEntityRepository.save(userEntity);
//            return true;
//        }
//        return false;
        UserConfirmEntity userConfirmEntity = userConfirmRepository.findByEmail(email);
        if (redisService.hasKey(email) && redisService.getSmsCertification(email).equals(code)) {
            redisService.removeSmsCertification(email);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean userPasswordReissue(String code, String email) throws Exception {
//        UserConfirmEntity userConfirmEntity = userConfirmRepository.findByEmail(email);
//        if (userConfirmEntity == null) throw new UsernameNotFoundException(email);
//        if (userConfirmEntity.getConfirmKey().equals(code)) {
//            String ePw = emailServiceImpl.sendReissuePassirmEntity;
//            UserConfirmEntity userEntity = userConfirmRepository.findByEmail(email);
////            userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(ePw));word(email);
////            authEntityRepository.save(userEntity);
//            return true;
//        }
//        return false;
        if (redisService.hasKey(email) && redisService.getSmsCertification(email).equals(code)) {
            redisService.removeSmsCertification(email);
        } else {
            return false;
        }
        return true;
    }
}