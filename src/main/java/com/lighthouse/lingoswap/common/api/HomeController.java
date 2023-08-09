package com.lighthouse.lingoswap.common.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<HttpHeaders> home(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(headers);
    }
}
