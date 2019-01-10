package com.chgyoo.barret.service;

import java.util.List;
import java.util.Map;

import com.chgyoo.barret.entity.Department;
import com.chgyoo.barret.entity.Group;
import com.chgyoo.barret.entity.User;
import com.chgyoo.barret.model.Command;
import com.chgyoo.barret.model.PageJson;
import com.chgyoo.barret.model.PageParam;
import com.chgyoo.barret.model.TreeNode;

public interface UserService {

  public PageJson getRoleUser(Command command, PageParam pageParam);

  public List<TreeNode> getDepartment();

  public List<User> getGroupUser(Command command);

  public PageJson getGroupUserPage(Command command, PageParam pageParam);

  public List<Department> getDeptByUser(String name);

  public Map<String, Object> getSelectRole(Command command);

  public void saveSelectRole(Command command);

  public PageJson getGroupTableData(PageParam pageParam, Command command);

  public void addOrUpdateGroup(Group group);

  public void delGroup(Command command);

  public List<TreeNode> searchDeptUser(Command command);

  public void saveGroupUser(Command command);

  public Map<String, Object> getUserInfo(Command command);

  public List<TreeNode> getUserPermission(Command command);

  public PageJson getCusUser(PageParam pageParam, Command command);

  public void addOrUpdateCusUser(User user);

  public void deleteCusUser(Command command);
}
