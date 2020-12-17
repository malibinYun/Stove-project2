package com.example.usermanagement.config;

import com.example.usermanagement.controller.resolver.AccountArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ArgumentResolverConfig implements WebMvcConfigurer {

    @Autowired
    private AccountArgumentResolver accountArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(accountArgumentResolver);
    }
}
