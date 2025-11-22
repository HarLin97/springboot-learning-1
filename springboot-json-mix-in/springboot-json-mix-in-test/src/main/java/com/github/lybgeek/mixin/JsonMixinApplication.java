package com.github.lybgeek.mixin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.lybgeek")
public class JsonMixinApplication {
    public static void main(String[] args) {
        SpringApplication.run(JsonMixinApplication.class, args);
    }
}
