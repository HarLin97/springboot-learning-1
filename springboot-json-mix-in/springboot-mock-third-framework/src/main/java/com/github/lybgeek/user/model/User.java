package com.github.lybgeek.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer id;
    private String username;      // 用户名（登录用）
    private String fullName;      // 真实姓名
    private String email;         // 邮箱
    private String phone;         // 手机号
    private String gender;        // 性别（男/女/未知）
    private Integer age;          // 年龄
    private String address;       // 地址
    @JsonSerialize(using = LocalDateTimeSerializer.class) // 序列化（对象转JSON）
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) // 反序列化（JSON转对象）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime registrationTime;  // 注册时间
    private boolean isActive;     // 是否激活

    public User(String username, String fullName, String email, String phone,
                String gender, Integer age, String address, boolean isActive) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.isActive = isActive;
    }



}