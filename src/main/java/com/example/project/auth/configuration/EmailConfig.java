package com.example.project.auth.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Slf4j
@Configuration
@RequiredArgsConstructor
@PropertySource(value = "classpath:/application-email.yml")
public class EmailConfig {

    private final Environment env;

    @Bean
    public JavaMailSender javaMailService() {
        log.info(env.toString());
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.naver.com");
        javaMailSender.setUsername(env.getProperty("AdminMail.id"));
        javaMailSender.setPassword(env.getProperty("AdminMail.password"));
        javaMailSender.setPort(Integer.parseInt(env.getProperty("mail.smtp.port")));
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");
        return javaMailSender;
    }
    private Properties getMailProperties()
    {
        Properties pt = new Properties();
        pt.put("mail.smtp.socketFactory.port", env.getProperty("mail.smtp.socketFactory.port"));
        pt.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        pt.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
        pt.put("mail.smtp.starttls.required", env.getProperty("mail.smtp.starttls.required"));
        pt.put("mail.smtp.socketFactory.fallback", env.getProperty("mail.smtp.socketFactory.fallback"));
        pt.put("mail.smtp.socketFactory.class", env.getProperty("mail.smtp.socketFactory.class"));
        return pt;
    }
}