package com.lighthouse.lingoswap.infra.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Profile({"local", "dev"})
@Service
public class S3Service {

    private static final long ONE_HOUR_IN_MS = 3600000;

    private final AmazonS3 s3;
    private final String bucketName;
    private final String profileKeyPrefix;

    public S3Service(final AmazonS3 s3,
                     @Value("${aws.s3.bucket-name}") final String bucketName,
                     @Value("${aws.s3.profile.prefix}") final String profileKeyPrefix) {
        this.s3 = s3;
        this.bucketName = bucketName;
        this.profileKeyPrefix = profileKeyPrefix;
    }

    public URL generatePresignedUrl(final String key) {
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, profileKeyPrefix + key, HttpMethod.PUT);
        req.setExpiration(new Date(System.currentTimeMillis() + ONE_HOUR_IN_MS));
        return s3.generatePresignedUrl(req);
    }

}
