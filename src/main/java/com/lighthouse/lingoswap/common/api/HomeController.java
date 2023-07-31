package com.lighthouse.lingoswap.common.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "Hello World\uD83E\uDEE0";
    }
}
