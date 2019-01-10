package com.chgyoo.barret.controller;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chgyoo.barret.entity.Group;
import com.chgyoo.barret.entity.User;
import com.chgyoo.barret.model.Command;
import com.chgyoo.barret.model.PageParam;
import com.chgyoo.barret.model.ResultJson;
import com.chgyoo.barret.service.UserService;


@RestController
@RequestMapping("/permission/user")
public class UserController {

  @Resource UserService userService;

  /**
   * 获取角色下的所有组织/自定义组/人员
   * @param command
   * @return
   */
  @RequestMapping("/getRoleUser")
  public ResultJson getRoleUser(Command command, PageParam pageParam) {
    return new ResultJson("查询成功", userService.getRoleUser(command, pageParam));
  }

  @RequestMapping("/getDept")
  public ResultJson getDept() {
    return new ResultJson("查询成功", userService.getDepartment());
  }

  /**
   * 获取组成员，组织架构/自定义组
   * @param command
   * @return
   */
  @RequestMapping("/getGroupUser")
  public ResultJson getGroupUser(Command command) {
    return new ResultJson("查询成功", userService.getGroupUser(command));
  }

  /**
   * 获取自定义组成员，带分页
   * @param command
   * @return
   */
  @RequestMapping("/getGroupUserPage")
  public ResultJson getGroupUserPage(Command command, PageParam pageParam) {
    return new ResultJson("查询成功", userService.getGroupUserPage(command, pageParam));
  }

  /**
   * 查找用户并且带组织架构信息
   * @param command
   * @return
   */
  @RequestMapping("/getDeptByUser")
  public ResultJson getDeptByUser(Command command) {
    return new ResultJson("查询成功", userService.getDeptByUser(command.getName()));
  }

  /**
   * 获取所有角色和已选择的角色，type 1-组织架构，2-用户，3-自定义组
   * @return
   */
  @RequestMapping("/getSelectRole")
  public ResultJson getSelectRole(Command command) {
    return new ResultJson("查询成功", userService.getSelectRole(command));
  }

  /**
   * 保存角色
   * @param command
   * @return
   */
  @RequestMapping("/saveSelectRole")
  public ResultJson saveSelectRole(Command command) {
    userService.saveSelectRole(command);
    return new ResultJson("保存成功");
  }

  /**
   * 获取自定义组表格数据
   * @param pageParam
   * @param command
   * @return
   */
  @RequestMapping("/getGroupTableData")
  public ResultJson getGroupTableData(PageParam pageParam, Command command) {
    return new ResultJson("查询成功", userService.getGroupTableData(pageParam, command));
  }

  /**
   * 自定义组新建&保存
   * @param group
   * @param command
   * @return
   * @throws Exception
   */
  @RequestMapping("/addOrUpdateGroup")
  public ResultJson addOrUpdateGroup(Group group, Command command) throws Exception {
    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
    if (!StringUtils.isEmpty(command.getFmtDate())) {
      group.setInvalidTime(sd.parse(command.getFmtDate()));
    } else {
      group.setInvalidTime(null);
    }
    userService.addOrUpdateGroup(group);
    return new ResultJson("保存成功");
  }

  @RequestMapping("/delGroup")
  public ResultJson delGroup(Command command) {
    userService.delGroup(command);
    return new ResultJson("删除成功");
  }

  /**
   * 用户查询，返回符合条件的组织架构树
   * @param command
   * @return
   */
  @RequestMapping("/getUserTreeData")
  public ResultJson getUserTreeData(Command command) {
    return new ResultJson("查询成功", userService.searchDeptUser(command));
  }

  /**
   * 保存组成员
   * @param command
   * @return
   */
  @RequestMapping("/saveGroupUser")
  public ResultJson saveGroupUser(Command command) {
    userService.saveGroupUser(command);
    return new ResultJson("保存成功");
  }


  @RequestMapping("/getUserInfo")
  public ResultJson getUserInfo (Command command) throws Exception {
    Map<String, Object> result = userService.getUserInfo(command);
    if (result == null) {
      throw new Exception("该用户不存在");
    } else {
      return new ResultJson("查询成功", result);
    }

  }

  @RequestMapping("/getUserPermission")
  public ResultJson getUserPermission(Command command) {
    return new ResultJson("查询成功", userService.getUserPermission(command));
  }

  @RequestMapping("/getCusUser")
  public ResultJson getCusUser(Command command, PageParam pageParam) {
    return new ResultJson("查询成功", userService.getCusUser(pageParam, command));
  }

  @RequestMapping("/addOrUpdateCusUser")
  public ResultJson addOrUpdateCusUser(User user) {
    userService.addOrUpdateCusUser(user);
    return new ResultJson("保存成功");
  }

  @RequestMapping("/deleteCusUser")
  public ResultJson deleteCusUser(Command command) {
    userService.deleteCusUser(command);
    return new ResultJson("保存成功");
  }

}
