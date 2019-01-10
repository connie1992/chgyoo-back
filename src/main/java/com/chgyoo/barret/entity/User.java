package com.chgyoo.barret.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class User  implements Serializable{
  private static final long serialVersionUID = 1L;

  private String code;
  private String id;
  private String account;
  private String name;
  private String password;
  private String post;
  // 公司
  private String book;
  private String bookName;
  private Department department;
  private String custom;
  private String status;
  private Date interfaceTime;
}
