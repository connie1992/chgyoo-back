package com.chgyoo.barret.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.chgyoo.barret.entity.Button;
import com.chgyoo.barret.entity.Menu;
import com.chgyoo.barret.entity.Role;
import com.chgyoo.barret.model.PageParam;

@Mapper public interface MenuMapper {

  /**
   * 获取菜单数据
   * @return
   */
  List<Menu> getMenuData();

  /**
   * 更新菜单
   * @param menu
   */
//  @Update({
//      "update t_menu t set t.name = #{name}, t.page_path = #{pagePath}, t.route_url = #{routeUrl}, t.icon = #{icon}, t.menu_order=#{menuOrder}, t.parent_id = #{parentId}, t.update_time = #{updateTime} where t.id = #{id}"})
  void updateMenu(Menu menu);

  /**
   * 插入菜单
   * @param menu
   */
  void insertMenu(Menu menu);

  /**
   * 删除按钮
   * @param id
   */
  @Delete({"delete t from t_button t where t.id = #{id}"})
  void deleteBtn(@Param("id") String id);

  void deleteMenu(@Param("ids") List<String> ids);
  /**
   * 获取子菜单
   * @param pid
   * @param menuOrder
   * @return
   */

  Menu getMenuByPid(@Param("pid") String pid, @Param("menuOrder") int menuOrder);

  /**
   * 顺序+1
   * @param id
   */
  @Update({"update t_menu set menu_order = menu_order + 1 where id = #{id}"})
  void updateOrderInc(@Param("id") String id);

  /**
   * 排序-1
    * @param id
   */
  @Update({"update t_menu set menu_order = menu_order - 1 where id = #{id}"})
  void updateOrderDesc(@Param("id") String id);

  @Update({"update t_menu set menu_order = menu_order - 1 where parent_id = #{pid} and menu_order > #{order}"})
  void updateOrderByPid(@Param("pid") String pid, @Param("order") int order);
  /**
   * 获取改父菜单下目前的最大排序
   * @param pid
   * @return
   */
  @Select({"select max(t.menu_order) from t_menu t where t.parent_id = #{pid}"})
  Integer getOrder(@Param("pid") String pid);

  /**
   * 获取菜单下的按钮
   * @param menuId
   * @return
   */
  List<Button> getBtns(@Param("menuId") String menuId);

  @Update({"update t_button set code = #{code}, name = #{name} where id = #{id}"})
  void updateBtn(Button btn);

  @Insert({"insert into t_button (id, code, name) values (#{id}, #{code}, #{name})"})
  void insertBtn(Button btn);

  @Insert({"insert into t_menu_btn (id, menu_id, btn_id) values (#{id}, #{menuId}, #{btnId})"})
  void insertMenuBtn(@Param("id") String id, @Param("menuId") String menuId,
      @Param("btnId") String btnId);

  //--------------角色----------------------
  int getRoleSize(@Param("key") String key);

  List<Role> getRoleTableData(@Param("pageParam") PageParam pageParam, @Param("key") String key);

  List<Role> getActiveRole();

  void updateRole(Role role);

  void insertRole(Role role);

  @Delete({"delete t from t_role t where t.id = #{id}"})
  void deleteRole(@Param("id") String id);

  List<Menu> getRoleMenu(@Param("roleId") String roleId);

  void removeRoleMenu(@Param("roleId") String roleId, @Param("menuArr") String[] menuArr);

  void saveRoleMenu(List<Map<String, String>> list);

  List<Button> getRoleBtn(@Param("roleId") String roleId, @Param("menuId") String menuId);

  void removeRoleBtn(@Param("roleId") String roleId, @Param("menuId") String menuId,
      @Param("btnArr") String[] btnArr);

  void saveRoleBtn(@Param("roleId") String roleId, @Param("menuId") String menuId,
      @Param("list") List<Map<String, String>> list);

  List<Menu> getRoleMenuBtn(@Param("roles") List<String> roles);
}
