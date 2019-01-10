package com.chgyoo.barret.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.chgyoo.barret.entity.Department;
import com.chgyoo.barret.entity.Group;
import com.chgyoo.barret.entity.Role;
import com.chgyoo.barret.entity.User;
import com.chgyoo.barret.model.PageParam;

@Mapper
public interface UserMapper {

  /**
   * 获取所有的组织架构
   * @return
   */
  List<Department> getDept();

  @Select({"select count(1) from ${roleTable} t where t.role_id = #{roleId}"})
  int getRoleItemsSize(@Param("roleTable") String roleTable, @Param("roleId") String roleId);

  /**
   * 获取角色下的组织架构
   * @param pageParam
   * @param roleId
   * @return
   */
  List<Department> getRoleDept(@Param("pageParam") PageParam pageParam,
      @Param("roleId") String roleId);

  /**
   * 获取角色下的自定义组
   * @param pageParam
   * @param roleId
   * @return
   */
  List<Group> getRoleGroup(@Param("pageParam") PageParam pageParam, @Param("roleId") String roleId);


  User getUserByAccount(@Param("account") String account);

  /**
   * 根据ID获取用户的部门信息
   * @param userId
   * @return
   */
  List<Department> getDepartmentByUser(@Param("userId") String userId);
  /**
   * 获取角色下的用户
   * @param pageParam
   * @param roleId
   * @return
   */
  List<User> getRoleUser(@Param("pageParam") PageParam pageParam, @Param("roleId") String roleId);

  @Select({"select id from t_user t where t.account = #{account}"})
  String getUserId(@Param("account") String account);

  /**
   * 获取自定义组下的用户大小
   * @param groupId
   * @return
   */
  @Select({"select count(1) from t_cus_group_user t where t.group_id = #{groupId}"})
  int getGroupUserSize(@Param("groupId") String groupId);

  /**
   * 获取组用户
   * @param groupId
   * @param isCustom 是否是自定义
   * @param pageParam
   * @return
   */
  List<User> getGroupUser(@Param("groupId") String groupId, @Param("isCustom") boolean isCustom,
      @Param("pageParam") PageParam pageParam);


  /**
   * 根据用户关键字获取用户完整用户信息和部门信息
   * @param key
   * @return
   */
  List<Department> searchDeptUser(@Param("key") String key);

  /**
   * 获取部门的路径
   * @param id 部门/用户 ID
   * @param isUser 是否用户
   * @return
   */
  List<String> getDeptPath(@Param("id") String id, @Param("isUser") boolean isUser);

  /**
   * 获取部门/用户/自定义组下的角色
   * @param params
   * @return
   */
  List<Role> getSelectRole(Map<String, Object> params);

  /**
   * 获取用户的组角色吗，，即用户属于的部门/自定义组下的角色
   * @param userId
   * @param depts
   * @param isUser
   * @return
   */
  List<Role> getUserGroupSelectRole(@Param("id") String userId, @Param("depts") List<String> depts,
      @Param("isUser") boolean isUser);


  void deleteRole(Map<String, Object> parmas);

  void insertRole(Map<String, Object> parmas);

  /**
   * 自定义组数据总条数
   * @param key 搜索条件
   * @return
   */
  @Select({"select count(1) from t_custom_group t where t.name like CONCAT(CONCAT('%', #{key}),'%')"})
  int getGroupDataSize(@Param("key") String key);

  /**
   * 自定义组表格数据
   * @param pageParam
   * @param key
   * @return
   */
  List<Group> getGroupTableData(@Param("pageParam") PageParam pageParam, @Param("key") String key);

  void insertGroup(Group group);

  void updateGroup(Group group);

  @Delete({"delete t from t_custom_group t where t.id = #{id}"})
  void delGroup(@Param("id") String id);

  /**
   * 搜索部门
   * @param key
   * @return
   */
  List<Department> searchDeptByName(@Param("key") String key);

  void addGroupUser(@Param("adds") List<Map<String, String>> addList);

  void deleteGroupUser(@Param("groupId") String groupId, @Param("ids") String[] ids);

  // -----------自定义用户------------------
  @Select({"select count(1) from t_user t where t.IS_CUSTOM = 1 and (t.account like CONCAT(CONCAT('%', #{key}),'%') or t.name like CONCAT(CONCAT('%', #{key}),'%'))"})
  int getCusUserSize(@Param("key") String key);

  List<User> getCusUser(@Param("pageParam") PageParam pageParam, @Param("key") String key);

  void insertCusUser(User user);

  void updateCusUser(User user);

  @Delete({"delete t from t_user t where t.id = #{id}"})
  void deleteCusUser(@Param("id") String id);

}
