package com.lighthouse.lingoswap.member.config;

import com.lighthouse.lingoswap.member.entity.Country;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "countries")
public class CountryFormConfiguration {

    private List<Country> list;

    // getters, setters
}
