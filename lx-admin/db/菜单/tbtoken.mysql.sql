-- 菜单初始SQL
INSERT INTO sys_menu(id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date)VALUES (1837396198101725186, 1067246875800000035, '用户Token', 'demo/tbtoken', NULL, 0, 'icon-desktop', 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1837396198101725187, 1837396198101725186, '查看', NULL, 'demo:tbtoken:page,demo:tbtoken:info', 1, NULL, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1837396198101725188, 1837396198101725186, '新增', NULL, 'demo:tbtoken:save', 1, NULL, 1, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1837396198101725189, 1837396198101725186, '修改', NULL, 'demo:tbtoken:update', 1, NULL, 2, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1837396198101725190, 1837396198101725186, '删除', NULL, 'demo:tbtoken:delete', 1, NULL, 3, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1837396198101725191, 1837396198101725186, '导出', NULL, 'demo:tbtoken:export', 1, NULL, 4, 1067246875800000001, now(), 1067246875800000001, now());
