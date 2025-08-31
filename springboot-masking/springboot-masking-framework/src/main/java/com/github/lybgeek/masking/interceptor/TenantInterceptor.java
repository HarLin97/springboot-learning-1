package com.github.lybgeek.masking.interceptor;

import com.github.lybgeek.masking.context.TenantContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 租户拦截器，从请求中提取租户ID并设置到上下文
 */
public class TenantInterceptor implements HandlerInterceptor {

    // 租户ID在请求头中的名称
    private static final String TENANT_HEADER = "X-Tenant-Id";
    // 租户ID在请求参数中的名称
    private static final String TENANT_PARAM = "tenantId";
    // 默认租户ID
    private static final String DEFAULT_TENANT = "default-tenant";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            // 1. 从请求头获取租户ID
            String tenantId = request.getHeader(TENANT_HEADER);

            // 2. 头中没有则从请求参数获取
            if (tenantId == null || tenantId.trim().isEmpty()) {
                tenantId = request.getParameter(TENANT_PARAM);
            }

            // 3. 都没有则使用默认租户
            if (tenantId == null || tenantId.trim().isEmpty()) {
                tenantId = DEFAULT_TENANT;
            }

            // 4. 设置租户ID到上下文
            TenantContext.setTenantId(tenantId.trim());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                               Object handler, Exception ex) {
        // 请求处理完成后清除租户上下文，避免线程复用问题
        TenantContext.clear();
    }
}
    