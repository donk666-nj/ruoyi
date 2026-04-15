<template>
  <div class="user-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            <span>新增用户</span>
          </el-button>
        </div>
      </template>
      <div class="search-form">
        <el-form :inline="true" :model="searchForm">
          <el-form-item label="用户名">
            <el-input v-model="searchForm.username" placeholder="请输入用户名" clearable></el-input>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table :data="pagedList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="nickname" label="昵称"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0"
              @change="handleStatusChange(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="scope">{{ formatTime(scope.row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination v-model:current-page="pagination.current" v-model:page-size="pagination.size"
          :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
          :total="filteredList.length"></el-pagination>
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="启用" :value="1"></el-option>
            <el-option label="禁用" :value="0"></el-option>
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
import { listUser, addUser, updateUser, delUser } from '@/api/user'

const loading = ref(false)
const userList = ref([])
const searchForm = ref({ username: '', status: '' })
const pagination = ref({ current: 1, size: 10 })

const loadUsers = async () => {
  loading.value = true
  try {
    const res = await listUser()
    userList.value = res.data
  } catch (e) {
    console.error('加载用户失败:', e)
  } finally {
    loading.value = false
  }
}

const filteredList = computed(() => {
  return userList.value.filter(u => {
    if (searchForm.value.username && !u.username.includes(searchForm.value.username)) return false
    if (searchForm.value.status !== '' && String(u.status) !== searchForm.value.status) return false
    return true
  })
})

const pagedList = computed(() => {
  const start = (pagination.value.current - 1) * pagination.value.size
  return filteredList.value.slice(start, start + pagination.value.size)
})

const handleSearch = () => { pagination.value.current = 1 }
const resetForm = () => { searchForm.value = { username: '', status: '' }; pagination.value.current = 1 }

const dialogVisible = ref(false)
const dialogTitle = ref('新增用户')
const form = ref({})

const handleAdd = () => {
  dialogTitle.value = '新增用户'
  form.value = { username: '', password: '', nickname: '', status: 1 }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑用户'
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    if (form.value.id) {
      await updateUser(form.value)
      ElMessage.success('修改成功')
    } else {
      await addUser(form.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadUsers()
  } catch (e) {
    console.error('提交失败:', e)
  }
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该用户?', '提示', { type: 'warning' }).then(async () => {
    await delUser(id)
    ElMessage.success('删除成功')
    loadUsers()
  }).catch(() => {})
}

const handleStatusChange = async (row) => {
  try {
    await updateUser({ id: row.id, status: row.status })
    ElMessage.success('状态修改成功')
  } catch (e) {
    row.status = row.status === 1 ? 0 : 1
  }
}

const formatTime = (timestamp) => {
  if (!timestamp) return ''
  return new Date(timestamp * 1000).toLocaleString()
}

onMounted(() => { loadUsers() })
</script>

<style scoped>
.user-container { padding: 0; flex: 1; display: flex; flex-direction: column; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 20px; }
.pagination { margin-top: 20px; display: flex; justify-content: flex-end; }
</style>
