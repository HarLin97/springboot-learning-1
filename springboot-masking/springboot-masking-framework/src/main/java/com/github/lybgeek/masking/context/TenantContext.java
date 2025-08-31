package com.github.lybgeek.masking.context;

/**
 * 租户上下文，使用ThreadLocal存储当前线程的租户ID
 */
public class TenantContext {

    // ThreadLocal存储租户ID，保证线程安全
    private static final ThreadLocal<String> TENANT_HOLDER = new ThreadLocal<>();

    /**
     * 设置当前租户ID
     */
    public static void setTenantId(String tenantId) {
        TENANT_HOLDER.set(tenantId);
    }

    /**
     * 获取当前租户ID
     */
    public static String getTenantId() {
        return TENANT_HOLDER.get();
    }

    /**
     * 清除当前租户ID
     */
    public static void clear() {
        TENANT_HOLDER.remove();
    }
}
    