package com.chgyoo.barret.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chgyoo.barret.entity.Department;
import com.chgyoo.barret.entity.Group;
import com.chgyoo.barret.entity.Menu;
import com.chgyoo.barret.entity.Role;
import com.chgyoo.barret.entity.User;
import com.chgyoo.barret.mapper.MenuMapper;
import com.chgyoo.barret.mapper.UserMapper;
import com.chgyoo.barret.model.Command;
import com.chgyoo.barret.model.Constant;
import com.chgyoo.barret.model.PageJson;
import com.chgyoo.barret.model.PageParam;
import com.chgyoo.barret.model.RoleFmt;
import com.chgyoo.barret.model.TreeNode;
import com.chgyoo.barret.service.MenuService;
import com.chgyoo.barret.service.UserService;
import com.chgyoo.barret.util.Cache;
import com.chgyoo.barret.util.TreeUtil;
import com.chgyoo.barret.util.Utils;

@Service
public class UserServiceImpl implements UserService {

  @Resource
  UserMapper userMapper;
  @Resource
  MenuMapper menuMapper;
  @Resource MenuService menuService;


  @Resource
  Cache cache;

  @Override
  public PageJson getRoleUser(Command command, PageParam pageParam) {
    int type = command.getType();
    // 组织架构
    if (type == 1) {
      PageJson pageJson = new PageJson(userMapper.getRoleItemsSize("t_dept_role", command.getRoleId()));
      if (pageJson.getTotalSize() > 0) {
        List<Department> departments = userMapper.getRoleDept(pageParam, command.getRoleId());
        // 组织路径处理
        getUserTree(departments);
        pageJson.setDataList(departments);
      }
      return pageJson;
    } else if (type == 2) {
      // 用户
      PageJson pageJson = new PageJson(userMapper.getRoleItemsSize("t_user_role", command.getRoleId()));
      if (pageJson.getTotalSize() > 0) {
        pageParam.setSortTableAlias("t1.");
        pageJson.setDataList(userMapper.getRoleUser(pageParam, command.getRoleId()));
      }
      return pageJson;
    } else {
      // 自定义组
      PageJson pageJson = new PageJson(userMapper.getRoleItemsSize("t_cus_group_role", command.getRoleId()));
      if (pageJson.getTotalSize() > 0) {
        pageJson.setDataList(userMapper.getRoleGroup(pageParam, command.getRoleId()));
      }
      return pageJson;
    }
  }

  @Override
  public List<TreeNode> getDepartment() {
    if (cache.getDept() == null) {
      List<Department> data = userMapper.getDept();
      List<TreeNode> fmtData = TreeUtil.convertTreeData(data);
      cache.setDept(fmtData);
    }
    return cache.getDept();
  }

  @Override public List<User> getGroupUser(Command command) {
    return userMapper.getGroupUser(command.getGroupId(), command.isCustom(), null);
  }

  @Override public PageJson getGroupUserPage(Command command, PageParam pageParam) {
    PageJson pageJson = new PageJson(userMapper.getGroupUserSize(command.getGroupId()));
    if (pageJson.getTotalSize() > 0) {
      pageParam.setSortTableAlias("t1.");
      pageJson.setDataList(userMapper.getGroupUser(command.getGroupId(), true, pageParam));
    }
    return pageJson;
  }

  @Override
  public List<Department> getDeptByUser(String name) {
    return userMapper.searchDeptUser(name);
  }

  @Override
  public Map<String, Object> getSelectRole(Command command) {
    // 获取所有在生效的角色
    List<Role> allRoles = menuMapper.getActiveRole();
    List<String> selectRoleIds = new ArrayList<>();
    Map<String, Object> params = getTableOpts(command.getType());
    params.put("id", command.getId());
    List<Role> selected = userMapper.getSelectRole(params);
    List<RoleFmt> roleList = new ArrayList<>();
    for (Role role : allRoles) {
      makeRoleFmt(roleList, role, false, "");
    }
    // 处理已失效的角色
    for (Role role : selected) {
      // 已选择的角色ID
      selectRoleIds.add(role.getId());
      int i = 0;
      for (; i < allRoles.size(); i++) {
        if (allRoles.get(i).getId().equals(role.getId())) {
          break;
        }
      }
      if (i == allRoles.size()) {
        makeRoleFmt(roleList, role, true, "（已失效）");
      }
    }

    // 如果是用户或者组织架构的话，则需要获取所在的组织架构或者自定义组的角色
    if (command.getType() != Constant.GROUP) {
      List<Role> userGroupRole = getGroupRole(command.getId(), command.getType() == Constant.USER);
      for (Role disabled : userGroupRole) {
        int i = 0;
        for (; i < roleList.size(); i++) {
          RoleFmt roleFmt = roleList.get(i);
          if (roleFmt.getKey().equals(disabled.getId())) {
            roleFmt.setDisabled(true);
            roleFmt.setLabel(roleFmt.getLabel() + "（父组织角色）");
            break;
          }
        }
        if (i == roleList.size()) {
          makeRoleFmt(roleList, disabled, true, "（父组织角色）");
        }
        if (selectRoleIds.contains(disabled.getId())) {
          selectRoleIds.remove(disabled.getId());
        }
        selectRoleIds.add(0, disabled.getId());
      }
    }
    Map<String, Object> reJson = new HashMap<>();
    reJson.put("data", roleList);
    reJson.put("target", selectRoleIds);
    return reJson;
  }

  private List<Role> getGroupRole(String id, boolean isUser) {
    List<String> paths = userMapper.getDeptPath(id, isUser);
    List<String> depts = new ArrayList<>();
    for (int i = 0; i < paths.size(); i++) {
      String[] pathArr = paths.get(i).split(";");
      for (int j = 0; j < pathArr.length; j++) {
        if (!depts.contains(pathArr[j]) && (isUser || (!isUser && !pathArr[j].equals(id)))) {
          depts.add(pathArr[j]);
        }
      }
    }
    if (depts.size() > 0) {
      return userMapper.getUserGroupSelectRole(id, depts, isUser);
    } else {
      return new ArrayList<>(0);
    }
  }

  private void makeRoleFmt(List<RoleFmt> roleFmtList, Role role, boolean disabled, String desc) {
    RoleFmt roleJson = new RoleFmt(role.getId(), role.getName() + desc, disabled);
    if (disabled) {
      roleFmtList.add(0, roleJson);
    } else {
      roleFmtList.add(roleJson);
    }
  }

  @Override
  public void saveSelectRole(Command command) {
    String[] adds= Utils.convertIds(command.getAddIds());
    String[] removes = Utils.convertIds(command.getRemoveIds());
    Map<String, Object> params = getTableOpts(command.getType());
    params.put("id", command.getId());
    if (adds.length > 0) {
      // 添加角色
      List<Map<String, String>> addList = new ArrayList<>(adds.length);
      for (int i = 0; i < adds.length; i++) {
        Map<String, String> item = new HashMap<>();
        item.put("id", Utils.createUUID());
        item.put("roleId", adds[i]);
        addList.add(item);
      }
      params.put("adds", addList);
      userMapper.insertRole(params);
    }
    if (removes.length > 0) {
      // 删除角色
      params.put("removes", removes);
      userMapper.deleteRole(params);
    }
  }

  /**
   * 获取表名 表字段
   * @param type 1-组织架构 2-用户 3-自定义组
   * @return
   */
  Map<String, Object> getTableOpts(int type) {
    String roleTable = "";
    String typeCloumn = "";
    switch (type) {
      case 1:
        roleTable = "t_dept_role";
        typeCloumn = "dept_id";
        break;
      case 2:
        roleTable = "t_user_role";
        typeCloumn = "user_id";
        break;
      case 3:
        roleTable = "t_cus_group_role";
        typeCloumn = "group_id";
        break;
    }
    Map<String, Object> params = new HashMap<>();
    params.put("roleTable", roleTable);
    params.put("typeColumn", typeCloumn);
    return params;
  }

  @Override
  public PageJson getGroupTableData(PageParam pageParam, Command command) {
    // 获取总数
    PageJson pageJson = new PageJson(userMapper.getGroupDataSize(command.getKey()));
    if (pageJson.getTotalSize() > 0) {
      List<Group> list = userMapper.getGroupTableData(pageParam, command.getKey());
      Utils.checkValid(list);
      pageJson.setDataList(list);
    } else {
      pageJson.setDataList(new ArrayList<>(0));
    }
    return pageJson;
  }

  @Override
  public void addOrUpdateGroup(Group group) {
    if (StringUtils.isEmpty(group.getId())) {
      group.setId(Utils.createUUID());
      group.setCreateTime(new Date());
      userMapper.insertGroup(group);
    } else {
      userMapper.updateGroup(group);
    }
  }

  @Override
  public void delGroup(Command command) {
    userMapper.delGroup(command.getIds());
  }

  @Override
  public List<TreeNode> searchDeptUser(Command command) {
    List<Department> deptUser = userMapper.searchDeptUser(command.getKey());
    List<TreeNode> resultTree = getUserTree(deptUser);
    return resultTree;
  }

  public List<TreeNode> getUserTree(List<Department> userList) {
    // 全树
    List<TreeNode> deptTree = this.getDepartment();
    List<TreeNode> deptPoint = deptTree;
    // 搜索结果树
    List<TreeNode> resultTree = new ArrayList<>();
    List<TreeNode> resultPoint = resultTree;

    int index = 0;
    for (Department deptUser : userList) {
      index++;
      String path = deptUser.getPath();
      String[] pathArr = path.split(";");
      // 根据路径查找组织架构树
      StringBuilder pathDetail = new StringBuilder();
      for (int i = 0; i < pathArr.length; i++) {
        // 经过第一次遍历，已经产生了一次目标树，现在目标树里面找
        boolean searchFlag = true;
        if (index > 1) {
          for (TreeNode node : resultPoint) {
            if (node.getId().equals(pathArr[i])) {
              pathDetail.append(((Department)node).getName()).append("<<");
              resultPoint = node.getChildren();
              searchFlag = false;
              break;
            }
          }
        }
        if (!searchFlag) {
          for (TreeNode node : deptPoint) {
            if (node.getId().equals(pathArr[i])) {
              deptPoint = node.getChildren();
              break;
            }
          }
          continue;
        }
        // 没有找到，则在全部组织架构树里面找
        for (int j = 0; j < deptPoint.size(); j++) {
          if (deptPoint.get(j).getId().equals(pathArr[i])) {
            Department tmp = (Department) deptPoint.get(j);
            pathDetail.append(tmp.getName()).append("<<");
            Department department = new Department(tmp.getId(), tmp.getCode(), tmp.getName());
            resultPoint.add(department);
            deptPoint = deptPoint.get(j).getChildren();
            resultPoint = department.getChildren();
            if (i == pathArr.length - 1) {
              department.setUserList(deptUser.getUserList());
              // 找完一个组织的路径，需要将指针指到开头
              resultPoint = resultTree;
              deptPoint = deptTree;
            }
            break;
          }
        }
        String pathDetailStr = pathDetail.toString();
        deptUser.setDetail(
            StringUtils.isEmpty(pathDetailStr) ? pathDetailStr : pathDetail.substring(0, pathDetail.length() - 2));
      }
    }
    return resultTree;
  }

  @Override
  public void saveGroupUser(Command command) {
    String[] adds = Utils.convertIds(command.getAddIds());
    if (adds.length > 0) {
      List<Map<String, String>> addList = new ArrayList<>(adds.length);
      for (int i = 0; i < adds.length; i++) {
        String id = adds[i];
        Map<String, String> addMap = new HashMap<>();
        addMap.put("id", Utils.createUUID());
        addMap.put("groupId", command.getGroupId());
        addMap.put("userId", id);
        addList.add(addMap);
      }
      userMapper.addGroupUser(addList);
    }
    String[] removes = Utils.convertIds(command.getRemoveIds());
    if (removes.length > 0) {
      userMapper.deleteGroupUser(command.getGroupId(), removes);
    }
  }

  @Override public Map<String, Object> getUserInfo(Command command) {
    User user = userMapper.getUserByAccount(command.getAccount());
    if (user == null) {
      return null;
    }
    if (!Constant.CUSTOMER_USER.equals(user.getCustom())) {
      List<Department> depts = userMapper.getDepartmentByUser(user.getId());
      // 这里需要考虑当一个用户对应多个组织时，前台需要显示的部门信息需要处理一下
      if (depts.size() <= 1) {
        user.setDepartment(depts.get(0));
      } else {
        String deptStr = "";
        for (Department department : depts) {
          deptStr += department.getName() + "/";
        }
        user.setDepartment(new Department("multiDept", "multiDept", deptStr));
      }
    }
    List<Map<String, Object>> books = new ArrayList<>();
    Map<String, Object> book = new HashMap<>();
    book.put("id", "1000");
    book.put("name", "深圳市华星光电技术有限公司");
    books.add(book);
    Map<String, Object> book2 = new HashMap<>();
    book2.put("id", "5000");
    book2.put("name", "武汉华星光电技术有限公司");
    books.add(book2);

    Map<String, Object> jsonObject = new HashMap<>();
    jsonObject.put("user", user);
    jsonObject.put("books", books);

    return jsonObject;
  }

  @Override
  public List<TreeNode> getUserPermission(Command command) {
    // 根据账户获取用户ID
    String userId = command.getId();
    command.setId(userId);
    command.setType(2);
    // 调用查询角色列表接口，返回数据格式：{data: [{key: roleId, label: roleName, disabled: true/false}…… ], target: [id1, id2]}
    Map<String, Object> roleJson = this.getSelectRole(command);
    List<RoleFmt> data = (List<RoleFmt>) roleJson.get("data");
    List<String> target = (List<String>) roleJson.get("target");
    List<String> roleIds= new ArrayList<>();
    for (int i = 0; i < data.size(); i++) {
      RoleFmt roleFmt = data.get(i);
       if (target.contains(roleFmt.getKey()) && !(roleFmt.getLabel().indexOf("过期") >= 0)) {
         roleIds.add(roleFmt.getKey());
       }
    }
    if (roleIds.size() == 0) {
      return new ArrayList<>();
    }
    // 获取角色的菜单和按钮
    List<Menu> menus = menuMapper.getRoleMenuBtn(roleIds);
    List<TreeNode> menuTree = menuService.getMenuData();
    // 菜单的所有父节点的ID
    List<String> paths = new ArrayList<>();
    // 菜单ID
    List<String> menuIds = new ArrayList<>();
    Map<String, List<String>> permissionMap = new HashMap<>();
    for (Menu menu : menus) {
      menuIds.add(menu.getId());
      String[] pathArr = menu.getPath().split(";");
      for (int i = 0; i < pathArr.length - 1; i++) {
        if (!paths.contains(pathArr[i])) {
          paths.add(pathArr[i]);
        }
      }
      permissionMap.put(menu.getId(), menu.getPermission());
    }
    permissionFilter(menuTree, menuIds, paths, permissionMap);
    return menuTree;
  }

  private void permissionFilter(List<TreeNode> menuTree, List<String> menus, List<String> paths, Map<String, List<String>> permissionMap) {
    for (int i = 0; i < menuTree.size(); i++) {
      TreeNode menu = menuTree.get(i);

      if (!paths.contains(menu.getId()) && !menus.contains(menu.getId())) {
        menuTree.remove(i--);
      } else {
        Menu obj = (Menu) menu;
        obj.setIcon(obj.getIcon() == null ? "" : " " + obj.getIcon());
      }
      if (menus.contains(menu.getId())) {
        ((Menu) menu).setPermission(permissionMap.get(menu.getId()));
      }
      if (menu.getChildren().size() > 0) {
        permissionFilter(menu.getChildren(), menus, paths, permissionMap);
      }
    }
  }

  @Override
  public PageJson getCusUser(PageParam pageParam, Command command) {
    PageJson pageJson = new PageJson(userMapper.getCusUserSize(command.getKey()));
    if (pageJson.getTotalSize() > 0) {
      pageJson.setDataList(userMapper.getCusUser(pageParam, command.getKey()));
    }
    return pageJson;
  }

  @Override
  public void addOrUpdateCusUser(User user) {
    if (StringUtils.isEmpty(user.getId())) {
      user.setId(Utils.createUUID());
      user.setInterfaceTime(new Date());
      userMapper.insertCusUser(user);
    } else {
      userMapper.updateCusUser(user);
    }
  }

  @Override public void deleteCusUser(Command command) {
    userMapper.deleteCusUser(command.getIds());
  }
}


