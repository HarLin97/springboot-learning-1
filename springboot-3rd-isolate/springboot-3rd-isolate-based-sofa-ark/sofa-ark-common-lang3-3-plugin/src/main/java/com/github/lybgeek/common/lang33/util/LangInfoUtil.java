package com.github.lybgeek.common.lang33.util;

import org.apache.commons.lang3.JavaVersion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LangInfoUtil {

    /**
     * 获取commons-lang3的版本信息、类加载器信息和支持的JDK版本
     * @return 包含信息的Map
     */
    public static Map<String, Object> getVersionInfo(String... executePlugin) {
        Map<String, Object> infoMap = new HashMap<>();

        if(executePlugin != null && executePlugin.length > 0){
            infoMap.put("executePlugin", executePlugin[0]);
        }

        
        // 封装版本信息
        String version = JavaVersion.class.getPackage().getImplementationVersion();
        infoMap.put("version", version);
        
        // 封装类加载器信息
        String classLoaderName = JavaVersion.class.getClassLoader().getClass().getName();
        infoMap.put("classLoader", classLoaderName);
        
        // 封装支持的JDK版本集合
        List<JavaVersion> supportedVersions = Arrays.stream(JavaVersion.values())
                .sorted()
                .distinct()
                .collect(Collectors.toList());
        infoMap.put("supportedJdkVersions", supportedVersions);
        
        return infoMap;
    }

    /**
     * 打印commons-lang3的信息（基于getVersionInfo()的结果）
     */
    public static void printVersionInfo(String moduleName) {
        Map<String, Object> infoMap = getVersionInfo();
        System.out.println("[" + moduleName + "] commons-lang3 版本: " + infoMap.get("version"));
        System.out.println("[" + moduleName + "] commons-lang3 使用的类加载器: " + infoMap.get("classLoader"));
        System.out.println("[" + moduleName + "] commons-lang3支持的JDK版本集合: " + infoMap.get("supportedJdkVersions"));
    }
}
    