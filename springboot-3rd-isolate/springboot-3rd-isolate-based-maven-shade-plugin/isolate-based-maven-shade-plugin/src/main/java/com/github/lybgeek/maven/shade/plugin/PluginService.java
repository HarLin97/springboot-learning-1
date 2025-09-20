package com.github.lybgeek.maven.shade.plugin;

import org.apache.commons.lang3.JavaVersion;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;


@Service
public class PluginService {
    private static final String COMMONS_LANG3_VERSION = "3.13.0";
    public void printVersion() {
        System.out.println("[插件] commons-lang3 版本: " + COMMONS_LANG3_VERSION);
        System.out.println("[插件] commons-lang3 类路径: " + JavaVersion.class.getPackage().getName());
        System.out.println("[插件] commons-lang3 使用的类加载器: " + JavaVersion.class.getClassLoader().getClass().getName());
        System.out.println("[插件] commons-lang3支持的JDK版本集合: " + Arrays.stream(JavaVersion.values()).sorted().distinct().collect(Collectors.toList()));

    }



}