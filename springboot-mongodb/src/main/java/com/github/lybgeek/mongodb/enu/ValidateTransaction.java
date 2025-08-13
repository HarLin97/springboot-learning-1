package com.github.lybgeek.mongodb.enu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  ValidateTransaction {

  YES("1", "是"), NO("0", "否");

  private final String value;

  private final String desc;

}
