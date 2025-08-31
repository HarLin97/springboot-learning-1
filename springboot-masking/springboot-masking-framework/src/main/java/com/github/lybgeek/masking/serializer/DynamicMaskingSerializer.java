package com.github.lybgeek.masking.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.lybgeek.masking.manager.TenantMaskingRuleManager;
import com.github.lybgeek.masking.model.MaskingRule;
import com.github.lybgeek.masking.service.MaskingService;
import com.github.lybgeek.masking.context.TenantContext;

import java.io.IOException;

/**
 * 动态脱敏序列化器，根据当前租户规则对字段值进行脱敏处理
 */
public class DynamicMaskingSerializer extends JsonSerializer<Object> {

    private final String className;
    private final String fieldName;
    private final TenantMaskingRuleManager ruleManager;
    private final MaskingService maskingService;

    // 构造函数改为接收类名、字段名和管理器，而非固定规则
    public DynamicMaskingSerializer(String className, String fieldName, 
                                   TenantMaskingRuleManager ruleManager, 
                                   MaskingService maskingService) {
        this.className = className;
        this.fieldName = fieldName;
        this.ruleManager = ruleManager;
        this.maskingService = maskingService;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        
        // 每次序列化时动态获取当前租户的规则
        MaskingRule rule = getCurrentTenantMaskingRule();
        
        if (rule != null) {
            String maskedValue = maskingService.maskValue(
                value.toString(),
                rule.getMaskingType(),
                rule.getPrefixLength(),
                rule.getSuffixLength(),
                rule.getMaskChar()
            );
            gen.writeString(maskedValue);
        } else {
            // 无规则时写入原始值
            gen.writeObject(value);
        }
    }
    
    // 动态获取当前租户的规则
    private MaskingRule getCurrentTenantMaskingRule() {
        String tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            return null;
        }
        
        return ruleManager.getClassRules(className).get(fieldName);
    }
}
    