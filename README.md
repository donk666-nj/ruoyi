# 前后端分离管理项目

## 项目简介

这是一个前后端分离的管理项目，前端使用Vue3，后端使用Spring Boot 2，数据库使用MySQL和Redis，包含完整的登录验证码功能。

## 技术栈

- **前端**：Vue3 + Vite + Vue Router + Axios
- **后端**：Spring Boot 2 + MyBatis + MyBatis-Plus
- **数据库**：MySQL + Redis

## 项目结构

### 前端结构
```
├── src/
│   ├── views/          # 页面组件
│   │   ├── Home.vue    # 首页
│   │   └── Login.vue   # 登录页
│   ├── router/         # 路由配置
│   │   └── index.js    # 路由定义
│   ├── main.js         # 应用入口
│   ├── App.vue         # 根组件
│   └── style.css       # 全局样式
├── public/             # 静态资源
├── index.html          # HTML模板
├── vite.config.js      # Vite配置
└── package.json        # 项目依赖
```

### 后端结构
```
├── src/main/java/com/example/admin/
│   ├── controller/     # 控制器
│   │   └── LoginController.java   # 登录相关接口
│   ├── service/        # 服务层
│   │   ├── UserService.java       # 用户服务接口
│   │   └── impl/UserServiceImpl.java  # 用户服务实现
│   ├── mapper/         # 数据访问层
│   │   └── UserMapper.java        # 用户Mapper
│   ├── entity/         # 实体类
│   │   └── User.java              # 用户实体
│   ├── config/         # 配置类
│   │   ├── CorsConfig.java        # CORS配置
│   │   └── RedisConfig.java       # Redis配置
│   ├── utils/          # 工具类
│   │   └── CaptchaUtil.java       # 验证码生成工具
│   └── AdminApplication.java      # 应用入口
├── src/main/resources/
│   ├── db/             # 数据库脚本
│   │   └── init.sql    # 初始化脚本
│   └── application.yml # 应用配置
└── pom.xml             # Maven依赖
```

## 快速开始

### 前端
1. 安装依赖：`npm install`
2. 启动开发服务器：`npm run dev`
3. 构建生产版本：`npm run build`

### 后端
1. 安装依赖：`mvn clean install`
2. 启动应用：`mvn spring-boot:run`
3. 构建jar包：`mvn clean package`

## 数据库配置

1. 创建MySQL数据库：`admin_db`
2. 执行初始化脚本：`src/main/resources/db/init.sql`
3. 配置Redis连接（默认端口6379）

## 登录测试

- 用户名：admin
- 密码：123456
- 验证码：点击图片刷新

## 注意事项

1. 确保MySQL和Redis服务正常运行
2. 前端默认运行在 `http://localhost:5173`
3. 后端默认运行在 `http://localhost:8080`
4. 如需修改配置，请编辑 `application.yml` 文件
