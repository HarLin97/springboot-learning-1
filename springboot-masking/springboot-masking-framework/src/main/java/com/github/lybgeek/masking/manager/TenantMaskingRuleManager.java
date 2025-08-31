package com.github.lybgeek.masking.manager;

import com.github.lybgeek.masking.cache.MaskingRuleCache;
import com.github.lybgeek.masking.client.RemoteMaskingRuleClient;
import com.github.lybgeek.masking.context.TenantContext;
import com.github.lybgeek.masking.model.MaskingRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 租户脱敏规则管理器，负责获取和管理租户的脱敏规则
 */
public class TenantMaskingRuleManager {

    private final RemoteMaskingRuleClient remoteClient;
    private final MaskingRuleCache ruleCache;
    
    // 三级缓存: tenantId -> className -> fieldName -> MaskingRule
    private final Map<String, Map<String, Map<String, MaskingRule>>> tenantClassFieldRulesCache = new ConcurrentHashMap<>();

    public TenantMaskingRuleManager(RemoteMaskingRuleClient remoteClient, MaskingRuleCache ruleCache) {
        this.remoteClient = remoteClient;
        this.ruleCache = ruleCache;
    }

    /**
     * 获取当前租户的所有脱敏规则
     */
    public List<MaskingRule> getCurrentTenantRules() {
        String tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            return getDefaultRules();
        }
        return getRulesByTenantId(tenantId);
    }

    /**
     * 根据租户ID获取脱敏规则
     */
    public List<MaskingRule> getRulesByTenantId(String tenantId) {
        List<MaskingRule> rules = ruleCache.getRules(tenantId);
        
        if (rules == null) {
            rules = remoteClient.fetchRulesForTenant(tenantId);
            
            if (rules != null) {
                ruleCache.putRules(tenantId, rules);
                updateTenantClassFieldRulesCache(tenantId, rules);
            } else {
                rules = getDefaultRules();
            }
        }
        
        return rules;
    }

    /**
     * 刷新指定租户的规则缓存
     */
    public void refreshRuleCache(String tenantId) {
        List<MaskingRule> newRules = remoteClient.fetchRulesForTenant(tenantId);
        if (newRules != null) {
            ruleCache.putRules(tenantId, newRules);
            updateTenantClassFieldRulesCache(tenantId, newRules);
        }
    }

    /**
     * 获取当前租户指定类的脱敏规则映射
     */
    public Map<String, MaskingRule> getClassRules(Class<?> clazz) {
        return getClassRules(clazz.getName());
    }
    
    /**
     * 获取当前租户指定类名的脱敏规则映射
     */
    public Map<String, MaskingRule> getClassRules(String className) {
        String tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            return getDefaultClassRules(className);
        }
        
        // 先从本地缓存获取
        Map<String, Map<String, MaskingRule>> classFieldRules = tenantClassFieldRulesCache.get(tenantId);
        if (classFieldRules != null && classFieldRules.containsKey(className)) {
            return classFieldRules.get(className);
        }
        
        // 缓存未命中时构建并缓存
        List<MaskingRule> rules = getRulesByTenantId(tenantId);
        Map<String, MaskingRule> fieldRules = new HashMap<>();
        for (MaskingRule rule : rules) {
            if (className.equals(rule.getClassName())) {
                fieldRules.put(rule.getFieldName(), rule);
            }
        }
        
        // 更新本地缓存
        classFieldRules = tenantClassFieldRulesCache.computeIfAbsent(tenantId, k -> new ConcurrentHashMap<>());
        classFieldRules.put(className, fieldRules);
        
        return fieldRules;
    }

    /**
     * 获取默认脱敏规则
     */
    private List<MaskingRule> getDefaultRules() {
        return remoteClient.fetchDefaultRules();
    }
    
    /**
     * 获取默认的类脱敏规则
     */
    private Map<String, MaskingRule> getDefaultClassRules(String className) {
        List<MaskingRule> defaultRules = getDefaultRules();
        Map<String, MaskingRule> fieldRules = new HashMap<>();
        for (MaskingRule rule : defaultRules) {
            if (className.equals(rule.getClassName())) {
                fieldRules.put(rule.getFieldName(), rule);
            }
        }
        return fieldRules;
    }
    
    /**
     * 更新租户的类字段规则缓存
     */
    private void updateTenantClassFieldRulesCache(String tenantId, List<MaskingRule> rules) {
        Map<String, Map<String, MaskingRule>> classFieldRules = new ConcurrentHashMap<>();
        
        for (MaskingRule rule : rules) {
            String className = rule.getClassName();
            classFieldRules.computeIfAbsent(className, k -> new ConcurrentHashMap<>())
                          .put(rule.getFieldName(), rule);
        }
        
        tenantClassFieldRulesCache.put(tenantId, classFieldRules);
    }
}
    