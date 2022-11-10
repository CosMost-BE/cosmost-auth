package com.example.project.auth.requestbody;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@NoArgsConstructor
@ConfigurationProperties(prefix = "email") // 설정 파일에서 email: 로 시작하는 properties
@Configuration
public class CreateAuthEmailRequest {

    private String name;

    private String email;

    private Long validTime;

}