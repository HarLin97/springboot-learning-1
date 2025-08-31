package com.github.lybgeek.masking.client;

import cn.hutool.core.util.URLUtil;
import com.github.lybgeek.masking.model.MaskingRule;
import com.github.lybgeek.masking.properties.MaskingProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.lybgeek.masking.constant.MaskingConstant.*;

/**
 * 远程脱敏规则客户端，通过HTTP请求获取租户的脱敏规则
 */
@Slf4j
public class RemoteMaskingRuleClient {

    private final RestTemplate restTemplate;
    private final MaskingProperties maskProperties;

    public RemoteMaskingRuleClient(MaskingProperties maskProperties) {
        this.maskProperties = maskProperties;
        this.restTemplate = new RestTemplate();
    }

    /**
     * 从远程服务获取指定租户的脱敏规则
     */
    public List<MaskingRule> fetchRulesForTenant(String tenantId) {
        try {
            // 构建请求URL
            String url = getRemoteServiceUrl( REMOTE_SERVICE_RULE_URL +"?tenantId=" + tenantId);

            ParameterizedTypeReference<List<MaskingRule>> responseType = new ParameterizedTypeReference<List<MaskingRule>>() {};
            // 发送GET请求获取规则
            ResponseEntity<List<MaskingRule>> response = restTemplate.exchange(url, HttpMethod.GET,null, responseType);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("获取租户["+tenantId+"]的脱敏规则失败: {}" , e.getMessage(),e);
        }
        
        // 获取失败时返回null
        return null;
    }

    /**
     * 获取默认脱敏规则
     */
    public List<MaskingRule> fetchDefaultRules() {
        try {
            String url = getRemoteServiceUrl( DEFAULT_REMOTE_SERVICE_RULE_URL);
            ParameterizedTypeReference<List<MaskingRule>> responseType = new ParameterizedTypeReference<List<MaskingRule>>() {};
            // 发送GET请求获取规则
            ResponseEntity<List<MaskingRule>> response = restTemplate.exchange(url, HttpMethod.GET,null, responseType);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception e) {
           log.error("获取默认脱敏规则失败: {}" ,e.getMessage(),e);
        }
        
        // 如果远程默认规则也获取失败，返回系统内置的最小化默认规则
        return getFallbackDefaultRules();
    }


    private String getRemoteServiceUrl(String relativePath) {
        return URLUtil.normalize(maskProperties.getRemoteServiceUrl() + "/" + maskProperties.getContextPath() + "/" + relativePath);
    }

    /**
     * 将远程响应转换为MaskingRule列表
     */
    @SuppressWarnings("unchecked")
    private List<MaskingRule> convertToMaskingRules(List<?> responseBody) {
        List<MaskingRule> rules = new ArrayList<>();
        
        for (Object item : responseBody) {
            if (item instanceof MaskingRule) {
                rules.add((MaskingRule) item);
            } else if (item instanceof java.util.Map) {
                // 处理Map类型的响应，转换为MaskingRule对象
                java.util.Map<String, Object> map = (java.util.Map<String, Object>) item;
                MaskingRule rule = new MaskingRule();
                rule.setClassName((String) map.get("className"));
                rule.setFieldName((String) map.get("fieldName"));
                rule.setMaskingType((String) map.get("maskingType"));
                rule.setPrefixLength(map.get("prefixLength") != null ? 
                        Integer.parseInt(map.get("prefixLength").toString()) : null);
                rule.setSuffixLength(map.get("suffixLength") != null ? 
                        Integer.parseInt(map.get("suffixLength").toString()) : null);
                rule.setMaskChar(map.get("maskChar") != null ? 
                        map.get("maskChar").toString().charAt(0) : '*');
                rules.add(rule);
            }
        }
        
        return rules;
    }

    /**
     * 最后的兜底默认规则，当所有获取方式都失败时使用
     */
    private List<MaskingRule> getFallbackDefaultRules() {
        List<MaskingRule> defaultRules = new ArrayList<>();
        // 添加一些基础的默认脱敏规则
        return Collections.emptyList();
    }
}
    