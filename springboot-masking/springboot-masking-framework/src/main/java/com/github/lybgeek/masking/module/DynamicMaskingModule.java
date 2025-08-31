package com.github.lybgeek.masking.module;


import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.lybgeek.masking.manager.TenantMaskingRuleManager;
import com.github.lybgeek.masking.serializer.DynamicMaskingSerializerModifier;
import com.github.lybgeek.masking.service.MaskingService;

/**
 * Jackson模块，注册脱敏序列化器修改器
 */
public class DynamicMaskingModule extends SimpleModule {

    public DynamicMaskingModule(TenantMaskingRuleManager ruleManager, MaskingService maskingService) {
        // 注册序列化器修改器
        setSerializerModifier(new DynamicMaskingSerializerModifier(ruleManager,maskingService));
    }
}
    