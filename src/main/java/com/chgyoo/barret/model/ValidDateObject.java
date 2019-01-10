package com.chgyoo.barret.model;

import java.util.Date;

import lombok.Data;

@Data
public class ValidDateObject {
  private String status;
  private Integer validDays;
  private Date createTime;
  private Date invalidTime;
  private String invalidTimeFmt;
}
