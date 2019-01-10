-- ------------初始化菜单数据----------------
-- ---1. demo
insert into t_menu 
  (id,
   name,
   page_path,
   route_url,
   icon,
   menu_order,
   parent_id,
   create_time,
   path)
values
  ('769d119c073c4e3bb9f8f7536603cb6f',
   'demo',
   'demo/demo',
   'demo',
   'iconfont icon-shoucang',
   1,
   'root',
   (select sysdate() from dual),
   '769d119c073c4e3bb9f8f7536603cb6f');
   
-- ---2. 菜单权限
insert into t_menu 
  (id,
   name,
   page_path,
   route_url,
   icon,
   menu_order,
   parent_id,
   create_time,
   path)
values
  ('87db82984e0a4af1bdac4edc4023615a',
   '系统管理',
   'index',
   'sysconfig',
   'iconfont icon-shezhi1',
   2,
   'root',
   (select sysdate() from dual),
   '87db82984e0a4af1bdac4edc4023615a');
   
insert into t_menu
  (id,
   name,
   page_path,
   route_url,
   icon,
   menu_order,
   parent_id,
   create_time,
   path)
values
  ('291e448717414cf5bb0636c9cc6b3404',
   '菜单权限',
   'setting/permission',
   'sysconfig/permission',
   'iconfont icon-menu',
   1,
   '87db82984e0a4af1bdac4edc4023615a',
   (select sysdate() from dual),
   '87db82984e0a4af1bdac4edc4023615a;291e448717414cf5bb0636c9cc6b3404');
   
insert into t_menu
  (id,
   name,
   page_path,
   route_url,
   icon,
   menu_order,
   parent_id,
   create_time,
   path)
values
  ('5cf24293305c4efaad2fc6bd740deb50',
   '菜单设置',
   'setting/menu-manage',
   'sysconfig/permission/menus',
   '',
   1,
   '291e448717414cf5bb0636c9cc6b3404',
   (select sysdate() from dual),
   '87db82984e0a4af1bdac4edc4023615a;291e448717414cf5bb0636c9cc6b3404;5cf24293305c4efaad2fc6bd740deb50');

insert into t_menu
  (id,
   name,
   page_path,
   route_url,
   icon,
   menu_order,
   parent_id,
   create_time,
   path)
values
  ('c55f6850059b4340a5098c55b0639b0a',
   '角色管理',
   'setting/role-manage',
   'sysconfig/permission/role',
   '',
   2,
   '291e448717414cf5bb0636c9cc6b3404',
   (select sysdate() from dual),
   '87db82984e0a4af1bdac4edc4023615a;291e448717414cf5bb0636c9cc6b3404;c55f6850059b4340a5098c55b0639b0a');  
   
insert into t_menu
  (id,
   name,
   page_path,
   route_url,
   icon,
   menu_order,
   parent_id,
   create_time,
   path)
values
  ('0a48b30817204f35a265a24f30b8409a',
   '角色授权',
   'setting/role-set',
   'sysconfig/permission/roleset',
   '',
   3,
   '291e448717414cf5bb0636c9cc6b3404',
   (select sysdate() from dual),
   '87db82984e0a4af1bdac4edc4023615a;291e448717414cf5bb0636c9cc6b3404;0a48b30817204f35a265a24f30b8409a'); 
   
-- ------------初始化菜单按钮数据----------------
-- -1. 按钮数据
insert into t_button 
(id, code, name)
(select '6a606bbee7b94707a335e5200c7a547b', 'add', '新增' from dual union all
select 'daa466d235c24a12b0357a2843e9b57a', 'delete', '删除' from dual union all
select '3049fe4dd23e4abaaf05bdd631f2489b', 'edit', '编辑' from dual);

-- -2. 菜单关联按钮
insert into t_menu_btn
(id, menu_id, btn_id)
(select '2e1de3cd72df45dd901f9d5eed4c4e92', '769d119c073c4e3bb9f8f7536603cb6f', 'daa466d235c24a12b0357a2843e9b57a' from dual union all
select 'abfa707881c44498a6d7f03a2777b440', '769d119c073c4e3bb9f8f7536603cb6f', '6a606bbee7b94707a335e5200c7a547b' from dual union all
select 'cda77d59af6c483c9303dd6e7375601d', '769d119c073c4e3bb9f8f7536603cb6f', '3049fe4dd23e4abaaf05bdd631f2489b' from dual);


  
-- ------------初始化管理员用户，账号：admin,密码：123456
insert into t_user
  (id, account, name, status, INTERFACE_TIME, IS_CUSTOM, PASSWORD, post)
values
  ('3bc4f7a07fb543dca1ba00760ad502a2',
   'admin',
   '管理员',
   '1',
   (select sysdate() from dual),
   '1',
   '123456',
   '拥有所有权限');


-- 初始化管理员角色
insert into t_role
  (id, name, code, create_time, `describe`)
values
  ('4bac2eae06e24a059a912a2052631bdf',
   '管理员',
   'admin',
   (select sysdate() from dual),
   '管理员');

-- 角色关联菜单
insert into t_role_menu
  (id, role_id, menu_id)
  (select '8b0857ef9027450a954c08f983735739', '4bac2eae06e24a059a912a2052631bdf', '5cf24293305c4efaad2fc6bd740deb50'
     from dual
   union all
   select 'a18dc24d0f844631862343d5ec6383f5', '4bac2eae06e24a059a912a2052631bdf', 'c55f6850059b4340a5098c55b0639b0a'
     from dual
   union all
   select '84ffedc03dfb4e54a7186810fefa993e', '4bac2eae06e24a059a912a2052631bdf', '0a48b30817204f35a265a24f30b8409a'
     from dual
	union all
   select '84ffedc03dfb4555a7186810fefa993e', '4bac2eae06e24a059a912a2052631bdf', '769d119c073c4e3bb9f8f7536603cb6f'
     from dual );

-- 角色关联按钮
insert into t_role_menu_btn 
(id, role_menu_id, btn_id)
(select '84ff44403dfb4555a7186810fefa993e', '84ffedc03dfb4555a7186810fefa993e', '6a606bbee7b94707a335e5200c7a547b' from dual union all 
select '84ff55503dfb4555a7186810fefa993e', '84ffedc03dfb4555a7186810fefa993e', 'daa466d235c24a12b0357a2843e9b57a' from dual union all 
select '84ffe6663dfb4555a7186810fefa993e', '84ffedc03dfb4555a7186810fefa993e', '3049fe4dd23e4abaaf05bdd631f2489b' from dual);

-- 管理员关联角色
insert into t_user_role (id, user_id, role_id) values 
('ec1ab88c31e84787aad96e02b82f4186', '3bc4f7a07fb543dca1ba00760ad502a2', '4bac2eae06e24a059a912a2052631bdf');
