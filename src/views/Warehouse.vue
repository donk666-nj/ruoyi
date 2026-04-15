<template>
  <div class="warehouse-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>仓库信息维护</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            <span>新增</span>
          </el-button>
        </div>
      </template>
      <div class="search-form">
        <el-form :inline="true" :model="searchForm">
          <el-form-item label="仓库编码">
            <el-input v-model="searchForm.storageNo" placeholder="请输入仓库编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="仓库描述">
            <el-input v-model="searchForm.storageName" placeholder="请输入仓库描述" clearable></el-input>
          </el-form-item>
          <el-form-item label="仓库类别">
            <el-select v-model="searchForm.storageTypeNo" placeholder="请选择" clearable>
              <el-option label="成品库" value="finished"></el-option>
              <el-option label="原料库" value="raw"></el-option>
              <el-option label="半成品库" value="semi"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="仓库类型">
            <el-select v-model="searchForm.storageAttr" placeholder="请选择" clearable>
              <el-option label="室内" value="indoor"></el-option>
              <el-option label="室外" value="outdoor"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否删除">
            <el-select v-model="searchForm.isDelete" placeholder="请选择" clearable>
              <el-option label="否" :value="0"></el-option>
              <el-option label="是" :value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table :data="pagedList" style="width: 100%" v-loading="loading">
        <el-table-column prop="storageNo" label="仓库编码" width="120"></el-table-column>
        <el-table-column prop="storageName" label="仓库描述"></el-table-column>
        <el-table-column prop="storageTypeNo" label="仓库类别" width="100">
          <template #default="scope">
            {{ typeMap[scope.row.storageTypeNo] || scope.row.storageTypeNo }}
          </template>
        </el-table-column>
        <el-table-column prop="storageAttr" label="仓库类型" width="80">
          <template #default="scope">
            {{ attrMap[scope.row.storageAttr] || scope.row.storageAttr }}
          </template>
        </el-table-column>
        <el-table-column prop="dummyFlag" label="是否虚拟库" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.dummyFlag === 1 ? 'warning' : 'success'">
              {{ scope.row.dummyFlag === 1 ? '是' : '否' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="validFlag" label="是否有效" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.validFlag === 1 ? 'success' : 'danger'">
              {{ scope.row.validFlag === 1 ? '有效' : '无效' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="storageAddrName" label="仓库地址"></el-table-column>
        <el-table-column prop="isDelete" label="是否删除" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.isDelete === 1 ? 'danger' : 'info'">
              {{ scope.row.isDelete === 1 ? '已删除' : '正常' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">修改</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination v-model:current-page="pagination.current" v-model:page-size="pagination.size"
          :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
          :total="tableData.length"></el-pagination>
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="100px" :rules="rules" ref="formRef">
        <el-form-item label="仓库编码" prop="storageNo">
          <el-input v-model="form.storageNo" placeholder="请输入仓库编码" :disabled="!!form.id"></el-input>
        </el-form-item>
        <el-form-item label="仓库描述" prop="storageName">
          <el-input v-model="form.storageName" placeholder="请输入仓库描述"></el-input>
        </el-form-item>
        <el-form-item label="客户名称" prop="custName">
          <el-input v-model="form.custName" placeholder="请输入客户名称"></el-input>
        </el-form-item>
        <el-form-item label="仓库类别" prop="storageTypeNo">
          <el-select v-model="form.storageTypeNo" placeholder="请选择仓库类别">
            <el-option label="成品库" value="finished"></el-option>
            <el-option label="原料库" value="raw"></el-option>
            <el-option label="半成品库" value="semi"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库类型" prop="storageAttr">
          <el-select v-model="form.storageAttr" placeholder="请选择仓库类型">
            <el-option label="室内" value="indoor"></el-option>
            <el-option label="室外" value="outdoor"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否虚拟库" prop="dummyFlag">
          <el-select v-model="form.dummyFlag" placeholder="请选择">
            <el-option label="否" :value="0"></el-option>
            <el-option label="是" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库地址" prop="storageAddrName">
          <el-input v-model="form.storageAddrName" placeholder="请输入仓库地址"></el-input>
        </el-form-item>
        <el-form-item label="是否有效" prop="validFlag">
          <el-select v-model="form.validFlag" placeholder="请选择">
            <el-option label="有效" :value="1"></el-option>
            <el-option label="无效" :value="0"></el-option>
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
import { listWarehouse, addWarehouse, updateWarehouse, delWarehouse } from '@/api/warehouse'

const typeMap = { finished: '成品库', raw: '原料库', semi: '半成品库' }
const attrMap = { indoor: '室内', outdoor: '室外' }

const loading = ref(false)
const tableData = ref([])
const searchForm = ref({ storageNo: '', storageName: '', storageTypeNo: '', storageAttr: '', isDelete: '' })
const pagination = ref({ current: 1, size: 10 })

const loadData = async () => {
  loading.value = true
  try {
    const params = {}
    if (searchForm.value.storageNo) params.storageNo = searchForm.value.storageNo
    if (searchForm.value.storageName) params.storageName = searchForm.value.storageName
    if (searchForm.value.storageTypeNo) params.storageTypeNo = searchForm.value.storageTypeNo
    if (searchForm.value.storageAttr) params.storageAttr = searchForm.value.storageAttr
    if (searchForm.value.isDelete !== '' && searchForm.value.isDelete !== null) params.isDelete = searchForm.value.isDelete
    const res = await listWarehouse(params)
    tableData.value = res.data
  } catch (e) {
    console.error('加载失败:', e)
  } finally {
    loading.value = false
  }
}

const pagedList = computed(() => {
  const start = (pagination.value.current - 1) * pagination.value.size
  return tableData.value.slice(start, start + pagination.value.size)
})

const handleSearch = () => { pagination.value.current = 1; loadData() }
const resetForm = () => {
  searchForm.value = { storageNo: '', storageName: '', storageTypeNo: '', storageAttr: '', isDelete: '' }
  handleSearch()
}

const dialogVisible = ref(false)
const dialogTitle = ref('新增仓库')
const formRef = ref(null)
const form = ref({})
const rules = {
  storageNo: [{ required: true, message: '请输入仓库编码', trigger: 'blur' }],
  custName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
  storageTypeNo: [{ required: true, message: '请选择仓库类别', trigger: 'change' }],
  storageAttr: [{ required: true, message: '请选择仓库类型', trigger: 'change' }],
  dummyFlag: [{ required: true, message: '请选择', trigger: 'change' }],
  storageAddrName: [{ required: true, message: '请输入仓库地址', trigger: 'blur' }],
  validFlag: [{ required: true, message: '请选择', trigger: 'change' }]
}

const handleAdd = () => {
  dialogTitle.value = '新增仓库'
  form.value = { storageNo: '', storageName: '', custName: '', storageTypeNo: '', storageAttr: '', dummyFlag: 0, validFlag: 1, storageAddrNo: '', storageAddrName: '' }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '修改仓库'
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    if (form.value.id) {
      await updateWarehouse(form.value)
      ElMessage.success('修改成功')
    } else {
      await addWarehouse(form.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (e) {
    console.error('提交失败:', e)
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm(`确认删除仓库 "${row.storageNo}" ?（逻辑删除）`, '提示', { type: 'warning' }).then(async () => {
    await delWarehouse(row.id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

onMounted(() => { loadData() })
</script>

<style scoped>
.warehouse-container { padding: 0; flex: 1; display: flex; flex-direction: column; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 20px; }
.pagination { margin-top: 20px; display: flex; justify-content: flex-end; }
</style>
