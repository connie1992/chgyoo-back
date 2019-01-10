-- --------------菜单----------------------
-- 1.菜单表
-- Create table
create table T_MENU
(
  id          CHAR(32) not null comment 'ID',
  name        VARCHAR(100) not null comment '名称',
  page_path   VARCHAR(200) not null comment '页面路径',
  route_url   VARCHAR(200) not null comment '路由地址',
  icon        VARCHAR(30) comment '图标',
  create_time DATETIME not null comment '创建时间',
  parent_id   VARCHAR(32) comment '父节点ID',
  menu_order  INT comment '菜单顺序',
  update_time DATETIME comment '更新时间',
  path        VARCHAR(200) comment '树路径'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MENU
  add constraint MENU_PK primary key (ID);



-- 2.按钮
-- Create table
create table T_BUTTON
(
  id   CHAR(32) not null comment 'ID',
  code VARCHAR(20) not null comment '编码',
  name VARCHAR(80) not null comment '名称'
);
alter table T_BUTTON
  add constraint BUTTON_PK primary key (ID);



-- 3.菜单-按钮
-- Create table
create table T_MENU_BTN
(
  id      CHAR(32) not null comment '菜单ID',
  menu_id CHAR(32) not null comment '按钮ID',
  btn_id  CHAR(32) not null comment '按钮ID'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MENU_BTN
  add constraint MENU_BTN_PK primary key (ID);
alter table T_MENU_BTN
  add constraint BUTTON_FK foreign key (BTN_ID)
  references T_BUTTON (ID) on delete cascade on update cascade;
alter table T_MENU_BTN
  add constraint MENU_FK foreign key (MENU_ID)
  references T_MENU (ID) on delete cascade on update cascade;



-- 4.角色表
-- Create table
create table T_ROLE
(
  id           CHAR(32) not null,
  `code`         VARCHAR(20) not null comment '编码',
  `name`         VARCHAR(80) not null comment '名称' ,
  create_time  DATETIME not null comment '创建时间',
  invalid_time DATETIME comment '过期时间',
  `describe`     VARCHAR(500) comment '描述'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ROLE
  add constraint ROLE_PK primary key (ID);


-- 5.角色-菜单
-- Create table
create table T_ROLE_MENU
(
  id      CHAR(32) not null,
  role_id CHAR(32) not null comment '角色ID',
  menu_id CHAR(32) not null comment '菜单ID'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ROLE_MENU
  add constraint ROLE_MENU_PK primary key (ID);
alter table T_ROLE_MENU
  add constraint ROLE_FK foreign key (ROLE_ID)
  references T_ROLE (ID) on delete cascade on update cascade;
alter table T_ROLE_MENU
  add constraint ROLE_MENU_FK foreign key (MENU_ID)
  references T_MENU (ID) on delete cascade on update cascade;


-- 6.角色-菜单-按钮表
-- Create table
create table T_ROLE_MENU_BTN
(
  id           CHAR(32) not null,
  role_menu_id CHAR(32) not null comment '角色菜单表ID',
  btn_id       CHAR(32) not null comment  '按钮ID'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ROLE_MENU_BTN
  add constraint ROLE_MENU_BTN_PK primary key (ID);
alter table T_ROLE_MENU_BTN
  add constraint ROLE_MENU_BTN_FK foreign key (ROLE_MENU_ID)
  references T_ROLE_MENU (ID) on delete cascade on update cascade;
alter table T_ROLE_MENU_BTN
  add constraint ROLE_MENU_BTN_FK2 foreign key (BTN_ID)
  references T_BUTTON (ID) on delete cascade on update cascade;


-- ---------------组织架构--------------------
-- 7.组织架构
-- Create table
create table T_DEPT
(
  id             VARCHAR(32) not null comment 'ID',
  code           VARCHAR(50) comment  '组织编码',
  name           VARCHAR(150) not null comment '组织名称',
  pgroup_id      VARCHAR(32) comment '父组织ID',
  path           VARCHAR(700) comment '到根节点的路径',
  interface_time DATETIME  comment '同步时间'
);
alter table T_DEPT
  add constraint DEPT_PK primary key (ID);
 

-- 8.用户表
-- Create table
create table T_USER
(
  id             VARCHAR(32) not null comment  '工号',
  name           VARCHAR(30) not null comment '姓名',
  account        VARCHAR(30) not null comment '账号',
  status         CHAR(1) comment '状态，1生效',
  interface_time datetime comment '同步时间',
  post           VARCHAR(100) comment '岗位',
  is_custom      CHAR(1) comment '是否自定义,1-自定义',
  password       VARCHAR(100) comment '密码'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_USER
  add constraint UER_PK primary key (ID);


-- 9.组织-用户对照表
-- Create table
create table T_DEPT_USER
(
  id      CHAR(32) not null ,
  dept_id VARCHAR(32) not null comment '组织ID',
  user_id VARCHAR(32) not null comment  '用户ID'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_DEPT_USER
  add constraint DEPT_USER_PK primary key (ID);
alter table T_DEPT_USER
  add constraint DEPT_USER_FK1 foreign key (DEPT_ID)
  references T_DEPT (ID) on delete cascade on update cascade;
alter table T_DEPT_USER
  add constraint DEPT_USER_FK2 foreign key (USER_ID)
  references T_USER (ID) on delete cascade on update cascade;


-- 10.组织-角色表
-- Create table
create table T_DEPT_ROLE
(
  id      CHAR(32) not null,
  dept_id VARCHAR(32) not null comment '组织ID',
  role_id CHAR(32) not null comment '角色ID'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_DEPT_ROLE
  add constraint DEPT_ROLE_PK primary key (ID);
alter table T_DEPT_ROLE
  add constraint DEPT_ROLE_FK1 foreign key (DEPT_ID)
  references T_DEPT (ID) on delete cascade on update cascade;
alter table T_DEPT_ROLE
  add constraint DEPT_ROLE_FK2 foreign key (ROLE_ID)
  references T_ROLE (ID) on delete cascade on update cascade;


-- 11.用户-角色表
-- Create table
create table T_USER_ROLE
(
  id      CHAR(32) not null ,
  user_id VARCHAR(32) not null comment '用户ID',
  role_id CHAR(32) not null comment '角色ID'
);
alter table T_USER_ROLE
  add constraint USER_ROLE_PK primary key (ID);
alter table T_USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (USER_ID)
  references T_USER (ID) on delete cascade on update cascade;
alter table T_USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (ROLE_ID)
  references T_ROLE (ID) on delete cascade on update cascade;


-- ---------------自定义组--------------------
-- 12.自定义组
-- Create table
create table T_CUSTOM_GROUP
(
  id           CHAR(32) not null ,
  code         VARCHAR(20) not null comment '编码',
  name         VARCHAR(100) not null comment '名称',
  `describe`     VARCHAR(300) comment '描述' ,
  create_time  datetime not null comment '创建时间',
  invalid_time datetime comment '失效时间，空表示永久有效'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CUSTOM_GROUP
  add constraint CUS_GROUP_PK primary key (ID);


-- 13.组-用户对照表
-- Create table
create table T_CUS_GROUP_USER
(
  id       CHAR(32) not null ,
  group_id CHAR(32) not null comment '组ID' ,
  user_id  VARCHAR(32) not null comment '用户ID'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CUS_GROUP_USER
  add constraint CG_USER_PK primary key (ID);
alter table T_CUS_GROUP_USER
  add constraint CG_USER_FK1 foreign key (GROUP_ID)
  references T_CUSTOM_GROUP (ID) on delete cascade on update cascade;
alter table T_CUS_GROUP_USER
  add constraint CG_USER_FK2 foreign key (USER_ID)
  references T_USER (ID) on delete cascade on update cascade;


-- 14.组-角色表
-- Create table
create table T_CUS_GROUP_ROLE
(
  id       CHAR(32) not null ,
  group_id CHAR(32) not null comment '组ID',
  role_id  CHAR(32) not null comment '角色ID'
);
alter table T_CUS_GROUP_ROLE
  add constraint GROUP_ROLE_PK primary key (ID);
alter table T_CUS_GROUP_ROLE
  add constraint GROUP_ROLE_FK1 foreign key (GROUP_ID)
  references T_CUSTOM_GROUP (ID) on delete cascade on update cascade;
alter table T_CUS_GROUP_ROLE
  add constraint GROUP_ROLE_FK2 foreign key (ROLE_ID)
  references T_ROLE (ID) on delete cascade on update cascade;
