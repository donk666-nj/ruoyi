<template>
  <div class="role-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>角色管理</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            <span>新增角色</span>
          </el-button>
        </div>
      </template>
      <div class="search-form">
        <el-form :inline="true" :model="searchForm">
          <el-form-item label="角色名称">
            <el-input v-model="searchForm.roleName" placeholder="请输入角色名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table :data="pagedList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="roleName" label="角色名称"></el-table-column>
        <el-table-column prop="roleKey" label="角色标识"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>
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
        <el-form-item label="角色名称">
          <el-input v-model="form.roleName" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item label="角色标识">
          <el-input v-model="form.roleKey" placeholder="请输入角色标识"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"></el-input>
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
import { listRole, addRole, updateRole, delRole } from '@/api/role'

const loading = ref(false)
const roleList = ref([])
const searchForm = ref({ roleName: '' })
const pagination = ref({ current: 1, size: 10 })

const loadRoles = async () => {
  loading.value = true
  try {
    const res = await listRole()
    roleList.value = res.data
  } catch (e) {
    console.error('加载角色失败:', e)
  } finally {
    loading.value = false
  }
}

const filteredList = computed(() => {
  return roleList.value.filter(r => {
    if (searchForm.value.roleName && !r.roleName.includes(searchForm.value.roleName)) return false
    return true
  })
})

const pagedList = computed(() => {
  const start = (pagination.value.current - 1) * pagination.value.size
  return filteredList.value.slice(start, start + pagination.value.size)
})

const handleSearch = () => { pagination.value.current = 1 }
const resetForm = () => { searchForm.value = { roleName: '' }; pagination.value.current = 1 }

const dialogVisible = ref(false)
const dialogTitle = ref('新增角色')
const form = ref({})

const handleAdd = () => {
  dialogTitle.value = '新增角色'
  form.value = { roleName: '', roleKey: '', remark: '', status: 1 }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑角色'
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    if (form.value.id) {
      await updateRole(form.value)
      ElMessage.success('修改成功')
    } else {
      await addRole(form.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadRoles()
  } catch (e) {
    console.error('提交失败:', e)
  }
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该角色?', '提示', { type: 'warning' }).then(async () => {
    await delRole(id)
    ElMessage.success('删除成功')
    loadRoles()
  }).catch(() => {})
}

const handleStatusChange = async (row) => {
  try {
    await updateRole({ id: row.id, status: row.status })
    ElMessage.success('状态修改成功')
  } catch (e) {
    row.status = row.status === 1 ? 0 : 1
  }
}

const formatTime = (value) => {
  if (!value) return ''
  // 如果是数字（Unix 时间戳秒），乘以 1000 转毫秒
  if (typeof value === 'number') {
    return new Date(value * 1000).toLocaleString()
  }
  // 如果是字符串（如 "2026-04-14 10:30:00"），直接解析
  const d = new Date(value)
  return isNaN(d.getTime()) ? value : d.toLocaleString()
}

onMounted(() => { loadRoles() })
</script>

<style scoped>
.role-container { padding: 0; flex: 1; display: flex; flex-direction: column; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 20px; }
.pagination { margin-top: 20px; display: flex; justify-content: flex-end; }
</style>
