-- 创建用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT 1,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建角色表
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  `role_key` varchar(50) NOT NULL,
  `status` int(11) DEFAULT 1,
  `order_num` int(11) DEFAULT 0,
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_key` (`role_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建菜单表
CREATE TABLE IF NOT EXISTS `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT 0,
  `menu_name` varchar(50) NOT NULL,
  `path` varchar(200) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `perms` varchar(100) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT 0,
  `order_num` int(11) DEFAULT 0,
  `status` int(11) DEFAULT 0,
  `create_by` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 插入默认管理员用户
INSERT IGNORE INTO `user` (`username`, `password`, `nickname`, `create_time`, `update_time`) VALUES
('admin', '123456', '管理员', UNIX_TIMESTAMP(), UNIX_TIMESTAMP());

-- 插入默认角色
INSERT IGNORE INTO `role` (`role_name`, `role_key`, `status`, `order_num`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
('管理员', 'admin', 1, 1, 'admin', NOW(), 'admin', NOW(), '系统管理员'),
('普通用户', 'user', 1, 2, 'admin', NOW(), 'admin', NOW(), '普通用户');

-- 插入默认菜单
INSERT IGNORE INTO `menu` (`parent_id`, `menu_name`, `path`, `component`, `perms`, `icon`, `type`, `order_num`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
(0, '系统管理', '/system', 'Layout', NULL, 'Setting', 0, 1, 0, 'admin', NOW(), 'admin', NOW(), '系统管理模块'),
(1, '用户管理', '/user', 'user/index', 'system:user:list', 'User', 1, 1, 0, 'admin', NOW(), 'admin', NOW(), '用户管理'),
(1, '角色管理', '/role', 'role/index', 'system:role:list', 'Avatar', 1, 2, 0, 'admin', NOW(), 'admin', NOW(), '角色管理'),
(1, '菜单管理', '/menu', 'menu/index', 'system:menu:list', 'Menu', 1, 3, 0, 'admin', NOW(), 'admin', NOW(), '菜单管理'),
(0, '内容管理', '/content', 'Layout', NULL, 'Document', 0, 2, 0, 'admin', NOW(), 'admin', NOW(), '内容管理模块'),
(5, '文章管理', '/article', 'article/index', 'content:article:list', 'Reading', 1, 1, 0, 'admin', NOW(), 'admin', NOW(), '文章管理'),
(5, '评论管理', '/comment', 'comment/index', 'content:comment:list', 'ChatLineSquare', 1, 2, 0, 'admin', NOW(), 'admin', NOW(), '评论管理');
