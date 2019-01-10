package com.chgyoo.barret.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.chgyoo.barret.model.TreeNode;

public class TreeUtil {
  /**
   * 查找节点其下的所有子节点
   *
   * @param list
   * @param ids
   */
  private static void findChildsDeep(List<TreeNode> list, List<String> ids) {
    for (TreeNode node : list) {
      ids.add(node.getId());
      findChildsDeep(node.getChildren(), ids);
    }
  }

  /**
   * 根据ID查找节点
   *
   * @param nodeList
   * @param id
   * @return
   */
  private static TreeNode findNode(List<TreeNode> nodeList, String id) {
    TreeNode node = null;
    for (int i = 0; i < nodeList.size(); i++) {
      TreeNode tmp = nodeList.get(i);
      if (tmp.getId().equals(id)) {
        node = tmp;
        break;
      } else {
        if (tmp.getChildren().size() > 0) {
          node = findNode(tmp.getChildren(), id);
          if (node != null) {
            break;
          }
        }
      }
    }
    return node;
  }

  public static List<String> getChildrenIds(List<TreeNode> list, String id) {
    List<String> ids = new ArrayList<>();
    findChildsDeep(findNode(list, id).getChildren(), ids);
    return ids;
  }


  private static void fmtData(List<?> data, TreeNode node) {
    Iterator<TreeNode> iterator = (Iterator<TreeNode>) data.iterator();
    while (iterator.hasNext()) {
      TreeNode tmp = iterator.next();
      if (!tmp.isFound() && tmp.getParentId() != null && tmp.getParentId().equals(node.getId())) {
        tmp.setChildren(new ArrayList<>());
        node.getChildren().add(tmp);
        node.setFound(true);
        fmtData(data, tmp);
      }
    }
  }

  public static List<TreeNode> convertTreeData(List<?> list) {
    List<TreeNode> re = new ArrayList<>();
    Iterator<TreeNode> iterator = (Iterator<TreeNode>) list.iterator();
    while (iterator.hasNext()) {
      TreeNode node = iterator.next();
      if (!node.isFound() && ("root".equals(node.getParentId()) || node.getParentId() == null)) {
        node.setChildren(new ArrayList<>());
        node.setFound(true);
        re.add(node);
        fmtData(list, node);
      }
    }
    return re;
  }

  public static TreeNode findParent(List<TreeNode> treeNodes, String parentId) {
    TreeNode target = null;
    for (TreeNode treeNode : treeNodes) {
      if (treeNode.getId().equals(parentId)) {
        target = treeNode;
        break;
      } else {
        target = findParent(treeNode.getChildren(), parentId);
      }
      if (target != null) {
        break;
      }
    }
    return target;
  }
}
