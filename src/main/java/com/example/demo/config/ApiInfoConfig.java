package com.example.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApiInfoConfig {

    @Value("${kakao.oauth.login.redirectUrl}")
    private String redirectUri;

    @Value("${kakao.oauth.appKey}")
    private String appKey;
}
