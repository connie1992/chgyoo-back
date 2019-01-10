package com.chgyoo.barret.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chgyoo.barret.entity.Button;
import com.chgyoo.barret.entity.Menu;
import com.chgyoo.barret.model.ResultJson;
import com.chgyoo.barret.service.MenuService;
import com.chgyoo.barret.util.Utils;

@RestController
@RequestMapping("/permission/menu")
public class MenuController {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Resource MenuService menuService;

  //---------------菜单操作-------------------------
  @RequestMapping("/getMenuData")
  public ResultJson getMenuData() {
    return new ResultJson("查询成功", menuService.getMenuData());
  }

  @RequestMapping("/addOrUpdateMenu")
  public ResultJson addOrUpdateMenu(Menu menu, String oldPid) {
    menuService.addOrupdate(menu, oldPid);
    return new ResultJson("保存成功");
  }

  @RequestMapping("/deleteMenu")
  public ResultJson deleteMenu(String ids) {
    menuService.delete(Utils.convertIds(ids)[0], true);
    return new ResultJson("删除成功");
  }

  @RequestMapping("menuUpDown")
  public ResultJson menuUpDown(Menu menu, boolean isUp) {
    menuService.menuUpDown(menu, isUp);
    return new ResultJson("保存成功");
  }

  //----------------按钮操作-------------------------
  @RequestMapping("/getBtnData")
  public ResultJson getBtnData(String menuId) {
    return new ResultJson("查询成功", menuService.getBtns(menuId));
  }

  @RequestMapping("/deleteBtn")
  public ResultJson deleteBtn(String ids) {
    menuService.delete(Utils.convertIds(ids)[0], false);
    return new ResultJson("删除成功");
  }

  @RequestMapping("/addOrUpdateBtn")
  public ResultJson addOrUpdateBtn(Button btn, String menuId) {
    menuService.addOrupdateBtn(btn, menuId);
    return new ResultJson("保存成功");
  }
}
