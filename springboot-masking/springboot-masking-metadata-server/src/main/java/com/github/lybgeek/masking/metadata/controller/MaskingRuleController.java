package com.github.lybgeek.masking.metadata.controller;

import com.github.lybgeek.masking.metadata.model.MaskingRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 脱敏规则提供接口，供脱敏服务获取不同租户的脱敏规则
 */
@RestController
@RequestMapping("/masking")
public class MaskingRuleController {

    /**
     * 获取指定租户的脱敏规则
     * @param tenantId 租户ID
     * @return 该租户的脱敏规则列表
     */
    @GetMapping("/rules")
    public ResponseEntity<List<MaskingRule>> getTenantMaskingRules(String tenantId, HttpServletRequest request) {
        // 实际应用中，这里应该从数据库或配置中心查询该租户的规则
        List<MaskingRule> rules = new ArrayList<>();
        if(StringUtils.isEmpty(tenantId)){
            tenantId = request.getHeader("X-Tenant-Id");
        }
        
        // 为不同租户设置不同的脱敏规则示例
        switch (tenantId) {
            case "tenant1":
                // 租户1的规则：手机号保留前3后4，姓名只保留姓氏
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "phone", "PHONE", 3, 4, '*'));
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "name", "NAME", 1, 0, '*'));
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "idCard", "ID_CARD", 6, 4, '*'));
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "bankAccount", "BANK_CARD", 4, 4, '*'));
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "email", "EMAIL", 2, 0, '*'));
                break;
                
            case "tenant2":
                // 租户2的规则：手机号保留前4后3，姓名保留前2位
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "phone", "PHONE", 4, 3, '*'));
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "name", "NAME", 2, 0, '*'));
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "idCard", "ID_CARD", 4, 4, '*'));
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "bankAccount", "BANK_CARD", 6, 3, '*'));
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "email", "EMAIL", 4, 0, '*'));
                break;
                
            case "tenant3":
                // 租户3的规则：使用#作为掩码字符
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "phone", "PHONE", 3, 4, '#'));
                rules.add(createRule("com.github.lybgeek.masking.test.model.User", "name", "NAME", 1, 0, '#'));
                rules.add(createRule("com.github.lybgeek.masking.test.model.Order", "paymentAccount", "BANK_CARD", 4, 3, '#'));
                break;
                
            case "default-tenant":
                rules.addAll(getMaskingRules());
                break;
                
            default:
                // 未知租户返回空规则列表，实际应用中可返回默认规则或报错
                return new ResponseEntity<>(rules, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    /**
     * 获取默认脱敏规则（当租户没有自定义规则时使用）
     * @return 默认脱敏规则列表
     */
    @GetMapping("/default")
    public ResponseEntity<List<MaskingRule>> getDefaultMaskingRules() {
        final List<MaskingRule> defaultRules = getMaskingRules();

        return new ResponseEntity<>(defaultRules, HttpStatus.OK);
    }

    private List<MaskingRule> getMaskingRules() {
        // 实际应用中，这里应该从数据库或配置中心查询默认规则
        List<MaskingRule> defaultRules = new ArrayList<>();

        // 默认规则设置
        defaultRules.add(createRule("com.github.lybgeek.masking.test.model.User", "phone", "PHONE", 3, 4, '*'));
        defaultRules.add(createRule("com.github.lybgeek.masking.test.model.User", "name", "NAME", 1, 0, '*'));
        defaultRules.add(createRule("com.github.lybgeek.masking.test.model.User", "idCard", "ID_CARD", 6, 4, '*'));
        defaultRules.add(createRule("com.github.lybgeek.masking.test.model.User", "bankAccount", "BANK_CARD", 4, 4, '*'));
        defaultRules.add(createRule("com.github.lybgeek.masking.test.model.User", "email", "EMAIL", 3, 0, '*'));
        defaultRules.add(createRule("com.github.lybgeek.masking.test.model.User", "address", "ADDRESS", 6, 4, '*'));
        defaultRules.add(createRule("com.github.lybgeek.masking.test.model.BankCard", "cardNumber", "BANK_CARD", 4, 4, '*'));
        defaultRules.add(createRule("com.github.lybgeek.masking.test.model.Order", "paymentAccount", "BANK_CARD", 4, 4, '*'));
        defaultRules.add(createRule("com.github.lybgeek.masking.test.model.Order", "shippingAddress", "ADDRESS", 8, 4, '*'));
        return defaultRules;
    }

    /**
     * 创建脱敏规则对象
     */
    private MaskingRule createRule(String className, String fieldName, String maskingType, 
                                  Integer prefixLength, Integer suffixLength, Character maskChar) {
        MaskingRule rule = new MaskingRule();
        rule.setClassName(className);    // 类的全限定名
        rule.setFieldName(fieldName);    // 字段名
        rule.setMaskingType(maskingType);// 脱敏类型
        rule.setPrefixLength(prefixLength); // 前缀保留长度
        rule.setSuffixLength(suffixLength); // 后缀保留长度
        rule.setMaskChar(maskChar);      // 掩码字符
        return rule;
    }
}
