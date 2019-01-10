package com.chgyoo.barret.service;

import java.util.List;

import com.chgyoo.barret.entity.Button;
import com.chgyoo.barret.entity.Menu;
import com.chgyoo.barret.model.TreeNode;


public interface MenuService {

  public List<TreeNode> getMenuData();

  public void addOrupdate(Menu menu, String oldPid);

  public void menuUpDown(Menu menu, boolean isUp);

  public void delete(String id, boolean isMenu);

  public List<Button> getBtns(String menuId);

  public void addOrupdateBtn(Button btn, String menuId);
}
