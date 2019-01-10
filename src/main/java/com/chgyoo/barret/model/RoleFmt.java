package com.chgyoo.barret.model;

import lombok.Data;

@Data
public class RoleFmt {
  private String key;
  private String label;
  private boolean disabled;

  public RoleFmt() {

  }

  public RoleFmt(String key, String label, boolean disabled) {
    this.key = key;
    this.label = label;
    this.disabled = disabled;
  }
}
