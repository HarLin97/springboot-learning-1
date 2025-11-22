package com.github.lybgeek.mixin.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(value = {"phone"})
public interface UserMaxIn {

    @JsonProperty("realName")
    String getFullName();


    @JsonProperty("createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    LocalDateTime getRegistrationTime();


}
