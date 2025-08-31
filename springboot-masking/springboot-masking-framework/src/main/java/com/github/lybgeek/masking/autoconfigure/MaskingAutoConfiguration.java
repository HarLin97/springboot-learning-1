package com.github.lybgeek.masking.autoconfigure;


import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.lybgeek.masking.cache.MaskingRuleCache;
import com.github.lybgeek.masking.client.RemoteMaskingRuleClient;
import com.github.lybgeek.masking.interceptor.TenantInterceptor;
import com.github.lybgeek.masking.manager.TenantMaskingRuleManager;
import com.github.lybgeek.masking.module.DynamicMaskingModule;
import com.github.lybgeek.masking.properties.MaskingProperties;
import com.github.lybgeek.masking.serializer.DynamicMaskingSerializer;
import com.github.lybgeek.masking.service.MaskingService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

/**
 * 自动配置类，负责装配所有脱敏相关组件
 */
@Configuration
@EnableConfigurationProperties(MaskingProperties.class)
@ConditionalOnProperty(prefix = MaskingProperties.PREFIX, value = "enable", havingValue = "true", matchIfMissing = true)
public class MaskingAutoConfiguration implements WebMvcConfigurer {
    
    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;


    /**
     * 租户拦截器Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public TenantInterceptor tenantInterceptor() {
        return new TenantInterceptor();
    }


    /**
     * 注册租户拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求
        registry.addInterceptor(tenantInterceptor())
                .addPathPatterns("/**")
                // 排除健康检查等不需要脱敏的接口
                .excludePathPatterns("/health", "/actuator/**");
    }


    /**
     * 远程规则客户端Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public RemoteMaskingRuleClient remoteMaskingRuleClient(MaskingProperties maskingProperties) {
        return new RemoteMaskingRuleClient(maskingProperties);
    }

    /**
     * 规则缓存Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public MaskingRuleCache maskingRuleCache(MaskingProperties maskingProperties) {
        return new MaskingRuleCache(maskingProperties.getCacheExpireMinutes());
    }

    /**
     * 租户规则管理器Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public TenantMaskingRuleManager tenantMaskingRuleManager(RemoteMaskingRuleClient remoteMaskingRuleClient,MaskingRuleCache maskingRuleCache) {
        return new TenantMaskingRuleManager(remoteMaskingRuleClient,maskingRuleCache);
    }


    @Bean
    @ConditionalOnMissingBean
    public MaskingService maskingService() {
        return new MaskingService();
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicMaskingModule dynamicMaskingModule(TenantMaskingRuleManager ruleManager,MaskingService maskingService) {
        return new DynamicMaskingModule(ruleManager,maskingService);
    }



//    /**
//     * 定义ObjectMapper Bean
//     */
//
//    private ObjectMapper bulidObjectMapper() {
//        // 处理已有ObjectMapper的情况，避免空指针
//        ObjectMapper objectMapper =  new ObjectMapper();
//        DynamicMaskingModule dynamicMaskingModule = defaultListableBeanFactory.getBean(DynamicMaskingModule.class);
//        // 安全注册脱敏模块
//        registerMaskingModule(objectMapper, dynamicMaskingModule);
//
//        return objectMapper;
//    }
//
//    /**
//     * 注册脱敏模块，增加空值检查
//     */
//    private void registerMaskingModule(ObjectMapper objectMapper, DynamicMaskingModule module) {
//        // 双重空值检查，确保安全
//        if (objectMapper != null && module != null) {
//            // 检查模块是否已注册，避免重复注册
//            if (CollectionUtil.isEmpty(objectMapper.getRegisteredModuleIds()) || !objectMapper.getRegisteredModuleIds().contains(module.getModuleName())) {
//                objectMapper.registerModule(module);
//            }
//        }
//    }
//
//
//
//    private ObjectMapper maskingObjectMapper() {
//        ObjectMapper objectMapper = null;
//        try {
//            objectMapper = defaultListableBeanFactory.getBean(ObjectMapper.class);
//            registerMaskingModule(objectMapper, defaultListableBeanFactory.getBean(DynamicMaskingModule.class));
//        } catch (BeansException e) {
//
//        }
//
//        if(objectMapper == null){
//            objectMapper = bulidObjectMapper();
//            defaultListableBeanFactory.registerSingleton("objectMapper", objectMapper);
//        }
//        return objectMapper;
//    }
//
//
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
////        // 清除默认的 MappingJackson2HttpMessageConverter
////        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
////
////        // 添加自定义的转换器（使用脱敏 ObjectMapper）
////        MappingJackson2HttpMessageConverter customConverter = new MappingJackson2HttpMessageConverter();
////        // 绑定脱敏 ObjectMapper
////        customConverter.setObjectMapper(maskingObjectMapper());
////
////        // 将自定义转换器放到第一位，确保优先使用
////        converters.add(0, customConverter);
//    }
}
    