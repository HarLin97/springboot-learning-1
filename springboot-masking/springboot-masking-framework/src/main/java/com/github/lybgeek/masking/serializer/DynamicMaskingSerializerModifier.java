package com.github.lybgeek.masking.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.github.lybgeek.masking.manager.TenantMaskingRuleManager;
import com.github.lybgeek.masking.service.MaskingService;

import java.util.List;

/**
 * 动态修改序列化器，为需要脱敏的字段替换为脱敏序列化器
 */
public class DynamicMaskingSerializerModifier extends BeanSerializerModifier {

    private final TenantMaskingRuleManager ruleManager;
    private final MaskingService maskingService;

    public DynamicMaskingSerializerModifier(TenantMaskingRuleManager ruleManager, MaskingService maskingService) {
        this.ruleManager = ruleManager;
        this.maskingService = maskingService;
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                                                     BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        
        String className = beanDesc.getBeanClass().getName();
        
        // 为所有字段创建动态序列化器（规则在序列化时动态获取）
        for (BeanPropertyWriter writer : beanProperties) {
            JsonSerializer<Object> serializer = new DynamicMaskingSerializer(
                className, 
                writer.getName(), 
                ruleManager, 
                maskingService
            );
            writer.assignSerializer(serializer);
        }
        
        return beanProperties;
    }
}
    