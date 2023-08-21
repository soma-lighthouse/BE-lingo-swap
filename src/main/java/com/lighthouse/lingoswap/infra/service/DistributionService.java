package com.lighthouse.lingoswap.infra.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"local", "dev"})
public class DistributionService {

    @Value("${aws.distribution.domain}")
    private String domain;

    public String generateUri(String fileName) {
        return "https://%s/%s".formatted(domain, fileName);
    }
}
