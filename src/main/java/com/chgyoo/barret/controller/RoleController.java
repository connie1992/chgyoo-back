package com.chgyoo.barret.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chgyoo.barret.entity.Button;
import com.chgyoo.barret.entity.Menu;
import com.chgyoo.barret.entity.Role;
import com.chgyoo.barret.model.PageParam;
import com.chgyoo.barret.model.ResultJson;
import com.chgyoo.barret.service.MenuService;
import com.chgyoo.barret.service.RoleService;

@RestController
@RequestMapping("/permission/role")
public class RoleController {

  @Resource RoleService roleService;

  @Resource MenuService menuService;

  @RequestMapping("/getTableData")
  public ResultJson getTableData(PageParam pageParam, String key) {
    System.out.println("获取角色列表……");
    return new ResultJson("查询成功", roleService.getTableData(pageParam, key));
  }

  @RequestMapping("/addOrUpdate")
  public ResultJson addOrUpdate(String fmtDate, Role role) throws Exception {
    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
    if (!StringUtils.isEmpty(fmtDate)) {
      role.setInvalidTime(sd.parse(fmtDate));
    } else {
      role.setInvalidTime(null);
    }
    roleService.addOrUpdate(role);
    return new ResultJson("保存成功");
  }

  @RequestMapping("/delete")
  public ResultJson delete(String ids) {
    roleService.delete(ids);
    return new ResultJson("删除成功");
  }

  @RequestMapping("/getMenuData")
  public ResultJson getMenuData(String roleId) {
    // 获取选择的菜单
    List<Menu> list = roleService.getRoleMenu(roleId);
    List<String> idList = new ArrayList<>(list.size());
    for (Menu menu : list) {
      idList.add(menu.getId());
    }
    return new ResultJson("查询成功", idList);
  }

  @RequestMapping("/saveMenu")
  public ResultJson saveMenu(String roleId, String addMenuIds, String removeMenuIds) {
    roleService.saveMenu(roleId, addMenuIds, removeMenuIds);
    return new ResultJson("保存菜单成功");
  }

  @RequestMapping("/getRoleBtn")
  public ResultJson getRoleBtn(String roleId, String menuId) {
    // 获取所有的按钮
    List<Button> btns = menuService.getBtns(menuId);
    // 获取选择的按钮
    List<Button> list = roleService.getRoleBtn(roleId, menuId);
    List<String> idList = new ArrayList<>(list.size());
    for (Button button : list) {
      idList.add(button.getId());
    }
    Map<String, Object> data = new HashMap<>();
    data.put("btns", btns);
    data.put("checks", idList);
    return new ResultJson("查询成功", data);
  }

  @RequestMapping("/saveBtn")
  public ResultJson saveBtn(String roleId, String menuId, String addBtnIds, String removeBtnIds) {
    roleService.saveBtn(roleId, menuId, addBtnIds, removeBtnIds);
    return new ResultJson("保存按钮成功");
  }
}
