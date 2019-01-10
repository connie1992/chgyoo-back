package com.chgyoo.barret.model;

import lombok.Data;

/**
 * 接收前台传递的参数
 */
@Data
public class Command {
  private String roleId;
  private String groupId;
  private boolean custom;
  private String name;
  private String id;
  private String ids;
  // 1-组织架构，2-用户，3-自定义组
  private int type;
  private String addIds;
  private String removeIds;
  private String key;
  private String fmtDate;
  private boolean user;

  private String account;
}
