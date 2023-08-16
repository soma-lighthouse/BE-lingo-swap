package com.lighthouse.lingoswap.infra.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class S3Service {

    private static final long FIFTEEN_MIN_IN_MS = 1800000;

    private final AmazonS3 s3;

    public String generatePreSignedUrl(String bucketName, String key) {
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.PUT);
        req.setExpiration(new Date(System.currentTimeMillis() + FIFTEEN_MIN_IN_MS));

        return s3.generatePresignedUrl(req).toString();
    }
}
