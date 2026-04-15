<template>
  <!-- 登录页独立布局 -->
  <div v-if="isLoginPage" class="login-page">
    <router-view />
  </div>
  <!-- 管理后台布局 -->
  <div v-else class="app-wrapper" :class="{ 'sidebar-collapse': !sidebar.opened }">
    <el-aside width="210px" :class="{ 'sidebar-collapse': !sidebar.opened }">
      <div class="sidebar">
        <div class="logo">
          <h1>若依管理系统</h1>
        </div>
        <el-menu
          :default-active="activeMenu"
          class="el-menu-vertical-demo"
          background-color="#304156"
          text-color="#fff"
          active-text-color="#409EFF"
          router
        >
          <el-menu-item index="/home">
            <el-icon><House /></el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/user">
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/role">
            <el-icon><Avatar /></el-icon>
            <span>角色管理</span>
          </el-menu-item>
          <el-menu-item index="/menu">
            <el-icon><Menu /></el-icon>
            <span>菜单管理</span>
          </el-menu-item>
        </el-menu>
      </div>
    </el-aside>
    <div class="main-container">
      <el-header height="60px" class="header">
        <div class="left">
          <el-button
            type="text"
            @click="toggleSidebar"
            style="color: #fff"
          >
            <el-icon><Menu /></el-icon>
          </el-button>
        </div>
        <div class="right">
          <el-dropdown>
            <span class="user">
              <el-avatar :size="32" :src="user.avatar || ''">
                {{ user.nickname ? user.nickname.charAt(0) : '用' }}
              </el-avatar>
              <span>{{ user.nickname || '未登录' }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">
                  <el-icon><SwitchButton /></el-icon>
                  <span>退出登录</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { House, User, Avatar, Menu, SwitchButton } from '@element-plus/icons-vue'

const store = useStore()
const router = useRouter()

const sidebar = computed(() => store.getters.sidebar)
const user = computed(() => store.getters.user)
const activeMenu = computed(() => {
  const route = router.currentRoute.value
  return route.path
})

const isLoginPage = computed(() => {
  return router.currentRoute.value.path === '/login'
})

const toggleSidebar = () => {
  store.dispatch('toggleSideBar')
}

const handleLogout = () => {
  store.dispatch('logout').then(() => {
    router.push('/login')
  })
}

onMounted(() => {
  if (!store.getters.isLoggedIn) {
    router.push('/login')
  }
})
</script>

<style scoped>
.app-wrapper {
  display: flex;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.sidebar {
  height: 100%;
  background-color: #304156;
  overflow-y: auto;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  border-bottom: 1px solid #405065;
}

.el-menu-vertical-demo {
  border-right: none;
  height: calc(100% - 60px);
}

.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.header {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header .left {
  display: flex;
  align-items: center;
}

.header .right {
  display: flex;
  align-items: center;
}

.user {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.user span {
  margin-left: 10px;
}

.sidebar-collapse {
  width: 64px !important;
}

.sidebar-collapse .logo h1 {
  font-size: 14px;
}

.sidebar-collapse .el-menu-item span {
  display: none;
}

.sidebar-collapse .el-menu-item .el-icon {
  margin-right: 0;
}
</style>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif;
  font-size: 14px;
  line-height: 1.5;
  color: #333;
  background-color: #f0f2f5;
  height: 100vh;
  margin: 0;
  padding: 0;
}

#app {
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}

.app-wrapper {
  height: 100vh;
  width: 100vw;
}

.el-header {
  padding: 0;
  height: 60px !important;
}

.el-main {
  padding: 20px;
  overflow-y: auto;
  flex: 1;
  display: flex !important;
  flex-direction: column;
}

.el-main > div {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.el-main .el-card {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.el-main .el-card .el-card__body {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.el-aside {
  transition: width 0.3s ease;
  height: 100vh;
}

.sidebar {
  height: 100vh;
}

.logo {
  height: 60px;
  border-bottom: 1px solid #405065;
}

.el-menu-vertical-demo {
  height: calc(100vh - 60px);
  border-right: none;
}

.main-container {
  flex: 1;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.login-page {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  z-index: 999;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .app-wrapper {
    flex-direction: column;
  }
  
  .el-aside {
    width: 100% !important;
    height: auto;
  }
  
  .sidebar {
    display: flex;
    flex-direction: row;
    overflow-x: auto;
    height: auto;
  }
  
  .logo {
    height: 50px;
    border-bottom: none;
    border-right: 1px solid #405065;
  }
  
  .el-menu-vertical-demo {
    display: flex;
    flex-direction: row;
    height: auto;
  }
  
  .el-menu-item {
    min-width: 80px;
  }
  
  .main-container {
    flex: 1;
    height: calc(100vh - 50px);
  }
}
</style>