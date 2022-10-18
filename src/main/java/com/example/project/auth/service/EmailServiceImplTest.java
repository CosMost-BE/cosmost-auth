//package com.example.project.auth.service;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EmailServiceImplTest {
//
//    private final JavaMailSender javaMailSender;
//
//    @Async
//    public String sendEmailTest(String email){
//
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom("kjh950601@naver.com");// naver
//        simpleMailMessage.setTo(email);
//        simpleMailMessage.setSubject("이메일 인증");
//        simpleMailMessage.setText("테스트");
//        javaMailSender.send(simpleMailMessage);
//        return "테스트완료";
//    }
//}
