package com.lighthouse.lingoswap.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ExternalApiConfig {

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3Client.builder().withRegion(Regions.AP_NORTHEAST_2).build();
    }
}
