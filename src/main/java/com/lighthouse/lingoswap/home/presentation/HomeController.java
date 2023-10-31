package com.lighthouse.lingoswap.home.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HomeController {

    @GetMapping(value = "/.well-known/assetlinks.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAssetLinks() {
        String json = "";
        try (InputStream stream = getClass().getResourceAsStream("/json/assetlinks.json")) {
            json = StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return json;
    }

}
