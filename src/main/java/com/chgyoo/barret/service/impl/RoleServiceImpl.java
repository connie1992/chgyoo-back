package com.chgyoo.barret.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.chgyoo.barret.entity.Button;
import com.chgyoo.barret.entity.Menu;
import com.chgyoo.barret.entity.Role;
import com.chgyoo.barret.mapper.MenuMapper;
import com.chgyoo.barret.model.PageJson;
import com.chgyoo.barret.model.PageParam;
import com.chgyoo.barret.service.RoleService;
import com.chgyoo.barret.util.Utils;

@Service
public class RoleServiceImpl implements RoleService {

  @Resource
  MenuMapper menuMapper;

  @Override
  @Transactional
  public PageJson getTableData(PageParam pageParam, String key) {
    PageJson pageJson = new PageJson(menuMapper.getRoleSize(key));
    if (pageJson.getTotalSize() != 0) {
      List<Role> list = menuMapper.getRoleTableData(pageParam, key);
      Utils.checkValid(list);
      pageJson.setDataList(list);
    }
    return pageJson;
  }

  @Override
  public void addOrUpdate(Role role) {
    if (StringUtils.isEmpty(role.getId())) {
      role.setId(Utils.createUUID());
      role.setCreateTime(new Date());
      menuMapper.insertRole(role);
    } else {
      menuMapper.updateRole(role);
    }
  }

  @Override
  public void delete(String ids) {
    menuMapper.deleteRole(Utils.convertIds(ids)[0]);
  }

  @Override
  public List<Menu> getRoleMenu(String roleId) {
    return menuMapper.getRoleMenu(roleId);
  }

  @Override
  public List<Button> getRoleBtn(String roleId, String menuId) {
    return menuMapper.getRoleBtn(roleId, menuId);
  }

  @Override
  @Transactional
  public void saveMenu(String roleId, String addMenuIds, String removeMenuIds) {
    String[] adds = Utils.convertIds(addMenuIds);
    String[] removes = Utils.convertIds(removeMenuIds);
    if (removes.length > 0) {
      menuMapper.removeRoleMenu(roleId, removes);
    }
    if (adds.length > 0) {
      List<Map<String, String>> list = new ArrayList<>(adds.length);
      for (int i = 0; i < adds.length; i++) {
        Map<String, String> map = new HashMap<>();
        map.put("id", Utils.createUUID());
        map.put("roleId", roleId);
        map.put("menuId", adds[i]);
        list.add(map);
      }
      menuMapper.saveRoleMenu(list);
    }
  }

  @Override
  @Transactional
  public void saveBtn(String roleId, String menuId, String addBtnIds, String removeBtnIds) {
    String[] add = Utils.convertIds(addBtnIds);
    String[] remove = Utils.convertIds(removeBtnIds);
    if (remove.length > 0) {
      menuMapper.removeRoleBtn(roleId, menuId, remove);
    }
    if (add.length > 0) {
      List<Map<String, String>> list = new ArrayList<>(add.length);
      for (int i = 0; i < add.length; i++) {
        Map<String, String> map = new HashMap<>();
        map.put("id", Utils.createUUID());
        map.put("btnId", add[i]);
        list.add(map);
      }
      menuMapper.saveRoleBtn(roleId, menuId, list);
    }

  }
}
