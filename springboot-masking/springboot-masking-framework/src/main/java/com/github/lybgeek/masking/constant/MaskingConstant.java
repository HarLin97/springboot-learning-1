package com.github.lybgeek.masking.constant;

/**
 * 脱敏类常量
 **/
public interface MaskingConstant {

    long DEFAULT_CACHE_EXPIRE_MINUTES = 60;

    String DEFAULT_REMOTE_SERVICE_BASE_URL = "/masking";
    String REMOTE_SERVICE_RULE_URL = DEFAULT_REMOTE_SERVICE_BASE_URL + "/rules";
    String DEFAULT_REMOTE_SERVICE_RULE_URL = REMOTE_SERVICE_RULE_URL + "/default";
}
