package com.github.lybgeek.masking.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.lybgeek.masking.model.MaskingRule;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 基于Caffeine的脱敏规则缓存实现
 */
public class MaskingRuleCache {

    // Caffeine缓存实例，key为租户ID，value为该租户的脱敏规则列表
    private final Cache<String, List<MaskingRule>> ruleCache;

    /**
     * 初始化Caffeine缓存，设置过期时间
     * 默认30分钟过期，可根据实际需求调整
     */
    public MaskingRuleCache(long cacheExpireMinutes) {
        this.ruleCache = Caffeine.newBuilder()
                // 写入后30分钟过期
                .expireAfterWrite(cacheExpireMinutes, TimeUnit.MINUTES)
                // 最大缓存1000个租户的规则
                .maximumSize(1000)
                // 记录缓存统计信息
                .recordStats()
                .build();
    }

    /**
     * 获取租户的脱敏规则
     * @param tenantId 租户ID
     * @return 脱敏规则列表，无缓存时返回null
     */
    public List<MaskingRule> getRules(String tenantId) {
        return ruleCache.getIfPresent(tenantId);
    }

    /**
     * 缓存租户的脱敏规则
     * @param tenantId 租户ID
     * @param rules 脱敏规则列表
     */
    public void putRules(String tenantId, List<MaskingRule> rules) {
        if (tenantId != null && rules != null) {
            ruleCache.put(tenantId, rules);
        }
    }

    /**
     * 清除指定租户的缓存
     * @param tenantId 租户ID
     */
    public void invalidate(String tenantId) {
        ruleCache.invalidate(tenantId);
    }

    /**
     * 清除所有缓存
     */
    public void invalidateAll() {
        ruleCache.invalidateAll();
    }
}
    