package com.lighthouse.lingoswap.infra.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Profile({"local", "dev"})
public class DistributionService {

    @Value("${aws.distribution.uri}")
    private String distributionUri;

    public String generateUri(String fileName) {
        if (StringUtils.hasText(fileName)) {
            return distributionUri + fileName;
        }
        return "";
    }
}
