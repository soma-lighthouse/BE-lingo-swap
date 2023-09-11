package com.lighthouse.lingoswap.auth.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class GoogleOAuthConfig {

    @Value("${spring.security.oauth.google.client-id}")
    public String clientId;

    @Bean
    HttpTransport httpTransport() {
        return new ApacheHttpTransport();
    }

    @Bean
    JsonFactory jsonFactory() {
        return new GsonFactory();
    }

    @Bean
    GoogleIdTokenVerifier verifier() {
        return new GoogleIdTokenVerifier.Builder(httpTransport(), jsonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }
}
