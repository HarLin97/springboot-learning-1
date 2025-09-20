package com.github.lybgeek.maven.shade.plugin.test;


import com.github.lybgeek.maven.shade.plugin.PluginService;
import org.apache.commons.lang3.JavaVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication(scanBasePackages = "com.github.lybgeek")
public class ShadePluginIsolateApplication implements ApplicationRunner {


    @Autowired
    private PluginService pluginService;

    public static void main(String[] args) {
        SpringApplication.run(ShadePluginIsolateApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("[主程序] commons-lang3 版本: " + JavaVersion.class.getPackage().getImplementationVersion());
        System.out.println("[主程序] commons-lang3 类路径: " + JavaVersion.class.getPackage().getName());
        System.out.println("[主程序] commons-lang3 使用的类加载器: " + JavaVersion.class.getClassLoader().getClass().getName());
        System.out.println("[主程序] commons-lang3支持的JDK版本集合: " + Arrays.stream(JavaVersion.values()).sorted().distinct().collect(Collectors.toList()));
        System.out.println("--------------------------------------------------------");
        pluginService.printVersion();


    }
}
