package com.chgyoo.barret.entity;

import java.io.Serializable;


import com.chgyoo.barret.model.ValidDateObject;

import lombok.Data;

@Data
public class Role extends ValidDateObject implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private String name;
  private String code;
  private String desc;
}
