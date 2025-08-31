package com.github.lybgeek.masking.test.model;

/**
 * 银行卡实体类，包含需要脱敏的银行卡信息
 */
public class BankCard {
    private String cardNumber;    // 卡号
    private String cardHolder;    // 持卡人
    private String bankName;      // 银行名称
    private String cardType;      // 卡类型
    private String expiryDate;    // 有效期

    // 无参构造函数
    public BankCard() {
    }

    // 全参构造函数
    public BankCard(String cardNumber, String cardHolder, String bankName, 
                   String cardType, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.bankName = bankName;
        this.cardType = cardType;
        this.expiryDate = expiryDate;
    }

    // Getter和Setter方法
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
    