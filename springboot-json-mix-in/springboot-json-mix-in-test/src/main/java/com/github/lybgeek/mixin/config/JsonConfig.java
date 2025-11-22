package com.github.lybgeek.mixin.config;


import com.github.lybgeek.mixin.user.UserMaxIn;
import com.github.lybgeek.user.model.User;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.mixIn(User.class, UserMaxIn.class);
    }
}
