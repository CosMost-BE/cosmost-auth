package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.entity.UserConfirmEntity;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.infrastructure.repository.UserConfirmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;

@PropertySource(value = "classpath:/application-email.yml")
@Slf4j
@RequiredArgsConstructor
@Service
@Component
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender emailSender;

    private final Environment env;
    private final UserConfirmRepository userConfirmRepository;
    private final AuthEntityRepository authEntityRepository;

    private MimeMessage reissuePassword(String to, String ePw)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("임시 비밀번호가 발급되었습니다.");

        String msgg = "";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 반갑습니다! COSMOST에서 보내드립니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 임시 비밀번호로 로그인하시고 비밀번호를 변경하여 사용하여 주세요.<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>임시 비밀 번호입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("kjh950601@naver.com","COSMOST"));//보내는 사람

        return message;
    }


    private MimeMessage createMessage(String to, String ePw)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("비밀번호 찾기를 위한 인증번호입니다.");

        String msgg = "";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 반갑습니다! COSMOST에서 보내드립니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 인증 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>비밀번호 찾기 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("kjh950601@naver.com","COSMOST"));//보내는 사람

        return message;
    }


    private MimeMessage idMessage(String to, String loginId)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+loginId);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("아이디 찾기를 위한 인증번호입니다.");

        String msgg = "";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 반갑습니다! COSMOST에서 보내드립니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 인증 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>아이디 찾기 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= loginId+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("kjh950601@naver.com","COSMOST"));//보내는 사람

        return message;
    }

    private MimeMessage createEmailConfirmMessage(String to, String ePw)throws Exception{

        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("회원가입 이메일 인증");

        String msgg = "";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 반갑습니다! COSMOST에서 보내드립니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("kjh950601@naver.com","COSMOST"));//보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for(int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) +97));
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

    @Override
    public String sendEmailId(String email) throws Exception { // 아이디 조회
        AuthEntity authEntity = authEntityRepository.findByEmail(email);
//        Optional<AuthEntity> auth = authEntityRepository.findByLoginId(email);
        String ePw = createKey();
        MimeMessage message = idMessage(email, ePw);
        log.info(String.valueOf(authEntity));
        log.info(String.valueOf(message));
        if (authEntity == null) {
            return "입력하신 이메일은 등록되지 않은 메일입니다.";
        }

        try {
            emailSender.send(message);
            UserConfirmEntity userConfirmEntity = userConfirmRepository.findByEmail(email);
            if (userConfirmEntity == null) {
                userConfirmRepository.save(UserConfirmEntity.builder()
                        .email(email)
                        .build());
            } else {
                userConfirmEntity.setConfirmKey(String.valueOf(authEntity));
                userConfirmRepository.save(userConfirmEntity);
            }
            log.info("이메일 전송, {}, pw", email, authEntity);
            return "success";
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
    @Override
    public String sendEmailPwd(String email) throws Exception {
        String ePw = createKey();
        MimeMessage message = createMessage(email, ePw); // 비밀번호 변경

        AuthEntity authEntity = authEntityRepository.findByEmail(email);
        if (authEntity == null) {
            return "입력하신 이메일은 등록되지 않은 메일입니다.";
        }
        try {
            emailSender.send(message);
            UserConfirmEntity userConfirmEntity = userConfirmRepository.findByEmail(email);
            if(userConfirmEntity == null){
                userConfirmRepository.save(UserConfirmEntity.builder()
                        .confirmKey(ePw)
                        .email(email)
                        .build());
            } else {
                userConfirmEntity.setConfirmKey(ePw);
                userConfirmRepository.save(userConfirmEntity);
            }
            log.info("이메일 전송, {}, pw", email, ePw);
            return "success";
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String sendConfirmCodeByEmail(String email) throws Exception {
        String ePw = createKey();
        MimeMessage message = createEmailConfirmMessage(email, ePw); // 이메일 사용 인증

        try {
            emailSender.send(message);
            UserConfirmEntity userConfirmEntity = userConfirmRepository.findByEmail(email);
            if(userConfirmEntity == null){
                userConfirmRepository.save(UserConfirmEntity.builder()
                        .confirmKey(ePw)
                        .email(email)
                        .build());
            } else {
                userConfirmEntity.setConfirmKey(ePw);
                userConfirmRepository.save(userConfirmEntity);
            }

            log.info("이메일 확인 인증 코드 전송, {}, pw", email, ePw);
            return "success";

        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String sendReissuePassword(String email) throws Exception {
        String ePw = createKey();
        log.info("패스워드 발송, {}, pw", email, ePw);
        MimeMessage message = reissuePassword(email, ePw); // 비밀번호 변경
        try {
            emailSender.send(message);
            return ePw;
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}

