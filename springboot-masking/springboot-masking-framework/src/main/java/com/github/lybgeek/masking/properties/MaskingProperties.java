package com.github.lybgeek.masking.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.lybgeek.masking.constant.MaskingConstant.DEFAULT_CACHE_EXPIRE_MINUTES;

/**
 * 脱敏属性类
 **/
@ConfigurationProperties(prefix = MaskingProperties.PREFIX)
public class MaskingProperties {
    public static final String PREFIX = "lybgeek.masking";
    /**
     * 远程服务地址
     */
    private String remoteServiceUrl;

    /**
     * 上下文路径
     */
    private String contextPath = "/";

    /**
     * 规则缓存过期时间（分钟）,默认60分钟
     */
    private long cacheExpireMinutes = DEFAULT_CACHE_EXPIRE_MINUTES;

    /**
     * 是否启用,默认启用
     */
    private boolean enable = true;

    public String getRemoteServiceUrl() {
        return remoteServiceUrl;
    }

    public void setRemoteServiceUrl(String remoteServiceUrl) {
        this.remoteServiceUrl = remoteServiceUrl;
    }

    public long getCacheExpireMinutes() {
        return cacheExpireMinutes;
    }

    public void setCacheExpireMinutes(long cacheExpireMinutes) {
        this.cacheExpireMinutes = cacheExpireMinutes;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
