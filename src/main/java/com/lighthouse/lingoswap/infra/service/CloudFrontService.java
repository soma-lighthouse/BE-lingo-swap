package com.lighthouse.lingoswap.infra.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Profile({"local", "dev"})
@Service
public class CloudFrontService {

    private final String cloudFrontEndpoint;

    public CloudFrontService(@Value("${aws.distribution.endpoint}") final String cloudFrontEndpoint) {
        this.cloudFrontEndpoint = cloudFrontEndpoint;
    }

    public String addEndpoint(final String fileName) {
        if (StringUtils.hasText(fileName)) {
            return cloudFrontEndpoint + fileName;
        }
        return "";
    }

}
