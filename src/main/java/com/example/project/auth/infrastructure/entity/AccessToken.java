package com.example.project.auth.infrastructure.entity;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Getter
@RedisHash(value = "access")
public class AccessToken {

    @Id
    private String id;
    @Indexed
    private String token;
    @TimeToLive
    private int expired;

    public AccessToken(String id, String token, int expired) {
        this.id = id;
        this.token = token;
        this.expired = expired;
    }
}
