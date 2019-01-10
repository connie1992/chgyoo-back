package com.chgyoo.barret.service;

import java.util.List;

import com.chgyoo.barret.entity.Button;
import com.chgyoo.barret.entity.Menu;
import com.chgyoo.barret.entity.Role;
import com.chgyoo.barret.model.PageJson;
import com.chgyoo.barret.model.PageParam;


public interface RoleService {
  public PageJson getTableData(PageParam pageParam, String key);

  public void addOrUpdate(Role role);

  public void delete(String ids);

  public List<Menu> getRoleMenu(String roleId);

  public List<Button> getRoleBtn(String roleId, String menuId);

  public void saveMenu(String roleId, String addMenuIds, String removeMenuIds);

  public void saveBtn(String roleId, String menuId, String addBtnIds, String removeBtnIds);
}
