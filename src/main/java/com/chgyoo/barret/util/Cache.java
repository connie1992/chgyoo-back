package com.chgyoo.barret.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chgyoo.barret.model.TreeNode;

import lombok.Data;

@Data
@Component
public class Cache {
  private List<TreeNode> dept;

}
