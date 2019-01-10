package com.chgyoo.barret.model;

import java.util.List;

import lombok.Data;

@Data
public class TreeNode {
  private String id;
  private String parentId;
  private String path;
  private List<TreeNode> children;
  private boolean found = false;
  private boolean last = false;
}
