package com.github.lybgeek.masking.test.model;

/**
 * 用户实体类，包含需要脱敏的个人信息
 */
public class User {
    private Long id;
    private String name;       // 姓名
    private String phone;      // 手机号
    private String email;      // 邮箱
    private String idCard;     // 身份证号
    private String bankAccount;// 银行账号
    private String address;    // 地址

    // 无参构造函数（JSON反序列化需要）
    public User() {
    }

    // 全参构造函数
    public User(Long id, String name, String phone, String email, 
               String idCard, String bankAccount, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.idCard = idCard;
        this.bankAccount = bankAccount;
        this.address = address;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
    