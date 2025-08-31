package com.github.lybgeek.masking.service;



/**
 * 脱敏服务，实现各种脱敏算法
 */
public class MaskingService {

    /**
     * 根据规则对值进行脱敏
     */
    public String maskValue(String value, String maskingType, 
                           Integer prefixLength, Integer suffixLength, 
                           Character maskChar) {
        
        if (value == null || value.isEmpty()) {
            return value;
        }
        
        // 处理默认参数
        char mask = (maskChar != null) ? maskChar : '*';
        int prefix = (prefixLength != null) ? prefixLength : getDefaultPrefix(maskingType);
        int suffix = (suffixLength != null) ? suffixLength : getDefaultSuffix(maskingType);


        // 如果指定了具体的前缀和后缀长度，直接使用
        if (prefix >= 0 && suffix >= 0) {
            return maskWithLengths(value, prefix, suffix, mask);
        }
        
        // 根据脱敏类型使用对应的算法
        switch (maskingType.toUpperCase()) {
            case "NAME":
                return maskName(value);
            case "PHONE":
                return maskPhone(value);
            case "EMAIL":
                return maskEmail(value);
            case "ID_CARD":
                return maskIdCard(value);
            case "BANK_CARD":
                return maskBankCard(value);
            case "ADDRESS":
                return maskAddress(value);
            case "ALL":
                return maskAll(value, mask);
            default:
                return value; // 不匹配任何类型则不脱敏
        }
    }

    /**
     * 根据前缀和后缀长度进行脱敏
     */
    private String maskWithLengths(String value, int prefix, int suffix, char mask) {
        // 确保前缀和后缀长度合理
        if (prefix < 0) {
            prefix = 0;
        }
        if (suffix < 0) {
            suffix = 0;
        }
        
        int length = value.length();
        
        // 如果前缀+后缀超过总长度，只保留前缀
        if (prefix + suffix >= length) {
            return prefix > 0 ? value.substring(0, prefix) + repeat(mask, length - prefix) : repeat(mask, length);
        }
        
        // 正常情况：前缀 + 掩码 + 后缀
        return value.substring(0, prefix) 
                + repeat(mask, length - prefix - suffix) 
                + value.substring(length - suffix);
    }

    /**
     * 姓名脱敏：只显示姓氏
     */
    private String maskName(String name) {
        if (name.length() <= 1) {
            return name;
        }
        return name.charAt(0) + repeat('*', name.length() - 1);
    }

    /**
     * 手机号脱敏：保留前3后4
     */
    private String maskPhone(String phone) {
        if (phone.length() == 11) { // 中国大陆手机号
            return phone.substring(0, 3) + "****" + phone.substring(7);
        }
        return maskWithLengths(phone, 3, 3, '*');
    }

    /**
     * 邮箱脱敏：保留前3位和域名
     */
    private String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 0) {
            return maskWithLengths(email, 3, 0, '*');
        }
        
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        
        int prefix = Math.min(3, username.length());
        return username.substring(0, prefix) + repeat('*', username.length() - prefix) + domain;
    }

    /**
     * 身份证号脱敏：保留前6后4
     */
    private String maskIdCard(String idCard) {
        if (idCard.length() == 18) {
            return idCard.substring(0, 6) + "********" + idCard.substring(14);
        }
        return maskWithLengths(idCard, 6, 4, '*');
    }

    /**
     * 银行卡号脱敏：保留后4位
     */
    private String maskBankCard(String bankCard) {
        return maskWithLengths(bankCard, 0, 4, '*');
    }

    /**
     * 地址脱敏：保留前6位，其余用*代替
     */
    private String maskAddress(String address) {
        return maskWithLengths(address, 6, 0, '*');
    }

    /**
     * 全部字符用掩码代替
     */
    private String maskAll(String value, char mask) {
        return repeat(mask, value.length());
    }

    /**
     * 获取默认前缀长度
     */
    private int getDefaultPrefix(String maskingType) {
        if (maskingType == null) {
            return 0;
        }
        
        switch (maskingType.toUpperCase()) {
            case "NAME": return 1;
            case "PHONE": return 3;
            case "EMAIL": return 3;
            case "ID_CARD": return 6;
            case "BANK_CARD": return 0;
            case "ADDRESS": return 6;
            default: return 0;
        }
    }

    /**
     * 获取默认后缀长度
     */
    private int getDefaultSuffix(String maskingType) {
        if (maskingType == null) {
            return 0;
        }
        
        switch (maskingType.toUpperCase()) {
            case "PHONE": return 4;
            case "ID_CARD": return 4;
            case "BANK_CARD": return 4;
            default: return 0;
        }
    }

    /**
     * 生成重复的掩码字符串（JDK8兼容实现）
     */
    private String repeat(char c, int times) {
        if (times <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
    