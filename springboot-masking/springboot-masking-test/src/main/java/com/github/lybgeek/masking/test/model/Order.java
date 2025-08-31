package com.github.lybgeek.masking.test.model;

import java.util.Date;

/**
 * 订单实体类，包含嵌套对象和需要脱敏的支付信息
 */
public class Order {
    private String orderId;         // 订单ID
    private User buyer;             // 买家（嵌套User对象）
    private double amount;          // 金额
    private String paymentMethod;   // 支付方式
    private String paymentAccount;  // 支付账号
    private Date createTime;        // 创建时间
    private String shippingAddress; // 收货地址

    // 无参构造函数
    public Order() {
    }

    // Getter和Setter方法
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
    