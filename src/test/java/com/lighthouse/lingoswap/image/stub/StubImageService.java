package com.lighthouse.lingoswap.image.stub;

import com.lighthouse.lingoswap.infra.service.ImageService;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class StubImageService implements ImageService {

    @Override
    public URL generatePresignedUrl(final String key) {
        try {
            return new URL("https://s3.amazonaws.com/%s".formatted(key));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URL getEndpoint() {
        try {
            return new URL("https://cloudfront.amazonaws.com");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
