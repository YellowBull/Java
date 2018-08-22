# 数据库的数据

```db
INSERT INTO `sys_user` (`id`, `password`, `name`) VALUES ('1', '$2a$04$5EzhLxOthTOMc5pN/Ozg1.wzrbijftcgEqXPouJt7igKIoxAmVWtO', 'vip');
INSERT INTO `sys_user` (`id`, `password`, `name`) VALUES ('2', '$2a$04$2/GN1BIPc6agihyHMQce4OSr34CgPCTRgiTQYEr51Wc1zm7u5eOTm', 'user');
INSERT INTO `sys_user` (`id`, `password`, `name`) VALUES ('3', '$2a$04$3Cksa3X0bYdYDkDm231w/uZ8BWJiV57OKTG9PZo8iFHTxNZ1jT77a', 'admin');
INSERT INTO `sys_user` (`id`, `password`, `name`) VALUES ('4', '$2a$04$3Cksa3X0bYdYDkDm231w/uZ8BWJiV57OKTG9PZo8iFHTxNZ1jT77a', 'super');

INSERT INTO `sys_role` (`id`, `name`,`uid`) VALUES ('1', 'USER','2');
INSERT INTO `sys_role` (`id`, `name`,`uid`) VALUES ('2', 'ADMIN','3');
INSERT INTO `sys_role` (`id`, `name`,`uid`) VALUES ('3', 'VIP','1');
INSERT INTO `sys_role` (`id`, `name`,`uid`) VALUES ('4', 'SUPER','4');


INSERT INTO `sys_resource` (`id`, `resource_name`,`resource_url`, `resource_id`) VALUES ('1', 'vip_url', '/vip', '1');
INSERT INTO `sys_resource` (`id`, `resource_name`,`resource_url`, `resource_id`) VALUES ('2', 'user_url', '/user', '2');
INSERT INTO `sys_resource` (`id`, `resource_name`,`resource_url`, `resource_id`) VALUES ('3', 'admin_url', '/admin', '3');
INSERT INTO `sys_resource` (`id`, `resource_name`,`resource_url`, `resource_id`) VALUES ('4', 'super_url', '/super', '4');


INSERT INTO `sys_resource_role` (`id`, `resource_id`, `role_id`) VALUES ('1', '2', '1');
INSERT INTO `sys_resource_role` (`id`, `resource_id`, `role_id`) VALUES ('2', '1', '3');
INSERT INTO `sys_resource_role` (`id`, `resource_id`, `role_id`) VALUES ('3', '3', '2');
INSERT INTO `sys_resource_role` (`id`, `resource_id`, `role_id`) VALUES ('4', '1', '2');
INSERT INTO `sys_resource_role` (`id`, `resource_id`, `role_id`) VALUES ('5', '4', '4');
INSERT INTO `sys_resource_role` (`id`, `resource_id`, `role_id`) VALUES ('6', '2', '4');
INSERT INTO `sys_resource_role` (`id`, `resource_id`, `role_id`) VALUES ('7', '3', '4');
INSERT INTO `sys_resource_role` (`id`, `resource_id`, `role_id`) VALUES ('8', '1', '4');
INSERT INTO `sys_resource_role` (`id`, `resource_id`, `role_id`) VALUES ('9', '2', '2');
INSERT INTO `sys_resource_role` (`id`, `resource_id`, `role_id`) VALUES ('10', '2', '3');


CREATE TABLE `persistent_logins` (
  `username` VARCHAR(64) NOT NULL,
  `series` VARCHAR(64) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `last_used` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
)
```