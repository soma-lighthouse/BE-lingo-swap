package com.lighthouse.lingoswap.infra.service;

import java.net.URL;

public interface ImageService {

    URL generatePresignedUrl(final String key);

}
