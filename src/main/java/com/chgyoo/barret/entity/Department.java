package com.chgyoo.barret.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.chgyoo.barret.model.TreeNode;

import lombok.Data;

@Data
public class Department extends TreeNode implements Serializable {
  private static final long serialVersionUID = 1L;
  private String code;
  private String name;
  private String detail;
  private List<User> userList;

  public Department() {

  }

  public Department(String id, String code, String name) {
    this.setId(id);
    this.code = code;
    this.setName(name);
    this.setChildren(new ArrayList<>());
  }
}
