package com.mediscreen.clientui.config;


import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor basicAuth() {
        return template -> {
            String auth = "user:password"; // ↩︎ identiques à ceux déclarés dans le Gateway
            String encoded = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            template.header("Authorization", "Basic " + encoded);
        };
    }
}