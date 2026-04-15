<template>
  <div class="menu-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>菜单管理</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            <span>新增菜单</span>
          </el-button>
        </div>
      </template>
      <div class="menu-tree">
        <el-tree :data="menuTree" :props="{ label: 'menuName', children: 'children' }"
          node-key="id" default-expand-all :expand-on-click-node="false">
          <template #default="{ node, data }">
            <span class="custom-tree-node">
              <span>{{ node.label }}</span>
              <span>
                <el-button type="primary" size="small" @click="handleEdit(data)" style="margin-right:5px">编辑</el-button>
                <el-button type="success" size="small" @click="handleAddChild(data)" style="margin-right:5px">新增子菜单</el-button>
                <el-button type="danger" size="small" @click="handleDelete(data.id)">删除</el-button>
              </span>
            </span>
          </template>
        </el-tree>
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="上级菜单">
          <el-select v-model="form.parentId" placeholder="请选择上级菜单">
            <el-option label="顶级菜单" :value="0"></el-option>
            <el-option v-for="m in menuOptions" :key="m.id" :label="m.label" :value="m.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单名称">
          <el-input v-model="form.menuName" placeholder="请输入菜单名称"></el-input>
        </el-form-item>
        <el-form-item label="路由地址">
          <el-input v-model="form.path" placeholder="请输入路由地址"></el-input>
        </el-form-item>
        <el-form-item label="组件路径">
          <el-input v-model="form.component" placeholder="请输入组件路径"></el-input>
        </el-form-item>
        <el-form-item label="权限标识">
          <el-input v-model="form.perms" placeholder="请输入权限标识"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="form.icon" placeholder="请输入菜单图标"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.orderNum" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="启用" :value="0"></el-option>
            <el-option label="禁用" :value="1"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMenuTree, addMenu, updateMenu, delMenu } from '@/api/menu'

const menuTree = ref([])

const loadMenus = async () => {
  try {
    const res = await getMenuTree()
    menuTree.value = res.data
  } catch (e) {
    console.error('加载菜单失败:', e)
  }
}

const menuOptions = computed(() => {
  const options = []
  const flatten = (menu, prefix = '') => {
    options.push({ id: menu.id, label: prefix + menu.menuName })
    if (menu.children) {
      menu.children.forEach(child => flatten(child, prefix + '├─ '))
    }
  }
  menuTree.value.forEach(menu => flatten(menu))
  return options
})

const dialogVisible = ref(false)
const dialogTitle = ref('新增菜单')
const form = ref({})

const handleAdd = () => {
  dialogTitle.value = '新增菜单'
  form.value = { parentId: 0, menuName: '', path: '', component: '', perms: '', icon: '', orderNum: 0, status: 0 }
  dialogVisible.value = true
}

const handleAddChild = (data) => {
  dialogTitle.value = '新增子菜单'
  form.value = { parentId: data.id, menuName: '', path: '', component: '', perms: '', icon: '', orderNum: 0, status: 0 }
  dialogVisible.value = true
}

const handleEdit = (data) => {
  dialogTitle.value = '编辑菜单'
  form.value = { ...data }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    if (form.value.id) {
      await updateMenu(form.value)
      ElMessage.success('修改成功')
    } else {
      await addMenu(form.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadMenus()
  } catch (e) {
    console.error('提交失败:', e)
  }
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该菜单?', '提示', { type: 'warning' }).then(async () => {
    await delMenu(id)
    ElMessage.success('删除成功')
    loadMenus()
  }).catch(() => {})
}

onMounted(() => { loadMenus() })
</script>

<style scoped>
.menu-container { padding: 0; flex: 1; display: flex; flex-direction: column; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.menu-tree { margin-top: 20px; }
.custom-tree-node { flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px; }
</style>
