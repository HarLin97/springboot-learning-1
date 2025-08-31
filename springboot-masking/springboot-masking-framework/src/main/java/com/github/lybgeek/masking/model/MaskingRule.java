package com.github.lybgeek.masking.model;

/**
 * 脱敏规则模型
 * 表示某个租户对某个类的某个字段的脱敏规则
 */
public class MaskingRule {
    // 类名（如User、Order）
    private String className;
    // 字段名（如phone、email）
    private String fieldName;
    // 脱敏类型（如PHONE、EMAIL、ID_CARD）
    private String maskingType;
    // 前缀保留长度
    private Integer prefixLength;
    // 后缀保留长度
    private Integer suffixLength;
    // 掩码字符（默认*）
    private Character maskChar;

    public MaskingRule() {
    }

    public MaskingRule(String className, String fieldName, String maskingType,
                      Integer prefixLength, Integer suffixLength, Character maskChar) {
        this.className = className;
        this.fieldName = fieldName;
        this.maskingType = maskingType;
        this.prefixLength = prefixLength;
        this.suffixLength = suffixLength;
        this.maskChar = maskChar;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMaskingType() {
        return maskingType;
    }

    public void setMaskingType(String maskingType) {
        this.maskingType = maskingType;
    }

    public Integer getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
    }

    public Integer getSuffixLength() {
        return suffixLength;
    }

    public void setSuffixLength(Integer suffixLength) {
        this.suffixLength = suffixLength;
    }

    public Character getMaskChar() {
        return maskChar;
    }

    public void setMaskChar(Character maskChar) {
        this.maskChar = maskChar;
    }
}
    