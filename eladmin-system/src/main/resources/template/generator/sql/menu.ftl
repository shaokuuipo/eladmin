<!--插入菜单的sql-->
INSERT INTO sys_menu (pid, sub_count, type, title, name, component, menu_sort, icon, path, i_frame, cache, hidden, permission, create_by, update_by, create_time, update_time)
 VALUES (null, 3, 1, '${apiAlias}', '${changeClassName}List', null, 1, 'menu', '${routePath}', false, false, false, '${changeClassName}:list', 'admin', 'admin', '${.now?string["yyyy-MM-dd HH:mm:ss"]}', '${.now?string["yyyy-MM-dd HH:mm:ss"]}');

<!--插入按钮的sql-->
INSERT INTO sys_menu (pid, sub_count, type, title, name, component, menu_sort, icon, path, i_frame, cache, hidden, permission, create_by, update_by, create_time, update_time)
VALUES ('填写插入菜单后的id', 0, 2, '${apiAlias}新增', null, '', 1, '', '', false, false, false, '${changeClassName}:add', 'admin', 'admin', '${.now?string["yyyy-MM-dd HH:mm:ss"]}', '${.now?string["yyyy-MM-dd HH:mm:ss"]}');

INSERT INTO sys_menu (pid, sub_count, type, title, name, component, menu_sort, icon, path, i_frame, cache, hidden, permission, create_by, update_by, create_time, update_time)
VALUES ('填写插入菜单后的id', 0, 2, '${apiAlias}修改', null, '', 2, '', '', false, false, false, '${changeClassName}:edit', 'admin', 'admin', '${.now?string["yyyy-MM-dd HH:mm:ss"]}', '${.now?string["yyyy-MM-dd HH:mm:ss"]}');

INSERT INTO sys_menu (pid, sub_count, type, title, name, component, menu_sort, icon, path, i_frame, cache, hidden, permission, create_by, update_by, create_time, update_time)
VALUES ('填写插入菜单后的id', 0, 2, '${apiAlias}删除', null, '', 3, '', '', false, false, false, '${changeClassName}:del', 'admin', 'admin', '${.now?string["yyyy-MM-dd HH:mm:ss"]}', '${.now?string["yyyy-MM-dd HH:mm:ss"]}');
