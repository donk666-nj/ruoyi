<template>
  <div class="locat-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>料位信息维护</span>
          <div>
            <el-button type="warning" @click="handleBatchAreaNo" :disabled="!selectedRows.length">批量修改区号</el-button>
            <el-button type="primary" @click="handleAdd">
              <el-icon><Plus /></el-icon>新增
            </el-button>
          </div>
        </div>
      </template>
      <div class="search-form">
        <el-form :inline="true" :model="searchForm">
          <el-form-item label="仓库编码">
            <el-select v-model="searchForm.storageNo" placeholder="请选择" clearable>
              <el-option v-for="w in warehouseList" :key="w.storageNo" :label="w.storageName" :value="w.storageNo"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="料位代码">
            <el-input v-model="searchForm.locationNo" placeholder="请输入料位代码" clearable></el-input>
          </el-form-item>
          <el-form-item label="料位类型">
            <el-select v-model="searchForm.locationType" placeholder="请选择" clearable>
              <el-option label="普通" value="normal"></el-option>
              <el-option label="重型" value="heavy"></el-option>
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
      <el-table :data="pagedList" style="width: 100%" v-loading="loading" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50"></el-table-column>
        <el-table-column prop="storageNo" label="仓库编码" width="100"></el-table-column>
        <el-table-column prop="locationNo" label="料位代码" width="120"></el-table-column>
        <el-table-column prop="locationType" label="料位类型" width="80"></el-table-column>
        <el-table-column prop="areaNo" label="区号" width="60"></el-table-column>
        <el-table-column prop="locationRow" label="行号" width="60"></el-table-column>
        <el-table-column prop="locationColumn" label="列号" width="60"></el-table-column>
        <el-table-column prop="locationLen" label="长度" width="70"></el-table-column>
        <el-table-column prop="locationWidth" label="宽度" width="70"></el-table-column>
        <el-table-column prop="locationHeight" label="高度" width="70"></el-table-column>
        <el-table-column prop="locationState" label="料位状态" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.locationState === 'empty' ? 'success' : 'warning'">
              {{ scope.row.locationState === 'empty' ? '空闲' : '占用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="locationAttrCode" label="料位属性" width="90"></el-table-column>
        <el-table-column prop="dummyFlag" label="虚拟料位" width="80">
          <template #default="scope">{{ scope.row.dummyFlag === 1 ? '是' : '否' }}</template>
        </el-table-column>
        <el-table-column prop="validFlag" label="是否有效" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.validFlag === 1 ? 'success' : 'danger'">{{ scope.row.validFlag === 1 ? '有效' : '无效' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="rackNo" label="料架号" width="80"></el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
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

    <!-- 新增/修改弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="650px">
      <el-form :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="仓库编码">
              <el-select v-model="form.storageNo" placeholder="请选择">
                <el-option v-for="w in warehouseList" :key="w.storageNo" :label="w.storageName" :value="w.storageNo"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="料位代码">
              <el-input v-model="form.locationNo" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="料位名称">
              <el-input v-model="form.locationName" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="料位类型">
              <el-select v-model="form.locationType" placeholder="请选择">
                <el-option label="普通" value="normal"></el-option>
                <el-option label="重型" value="heavy"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="行号"><el-input v-model="form.locationRow" placeholder="行号"></el-input></el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="列号"><el-input v-model="form.locationColumn" placeholder="列号"></el-input></el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="料位状态">
              <el-select v-model="form.locationState" placeholder="请选择">
                <el-option label="空闲" value="empty"></el-option>
                <el-option label="占用" value="occupied"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="长度"><el-input v-model="form.locationLen" placeholder="长度"></el-input></el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="宽度"><el-input v-model="form.locationWidth" placeholder="宽度"></el-input></el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="高度"><el-input v-model="form.locationHeight" placeholder="高度"></el-input></el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="料位属性">
              <el-input v-model="form.locationAttrCode" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="虚拟料位">
              <el-select v-model="form.dummyFlag" placeholder="请选择">
                <el-option label="否" :value="0"></el-option>
                <el-option label="是" :value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否有效">
              <el-select v-model="form.validFlag" placeholder="请选择">
                <el-option label="有效" :value="1"></el-option>
                <el-option label="无效" :value="0"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="区号"><el-input v-model="form.areaNo" placeholder="请输入"></el-input></el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="X坐标"><el-input v-model="form.coordinateX"></el-input></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="Y坐标"><el-input v-model="form.coordinateY"></el-input></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="Z坐标"><el-input v-model="form.coordinateZ"></el-input></el-form-item></el-col>
        </el-row>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" placeholder="请输入备注"></el-input></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 批量修改区号弹窗 -->
    <el-dialog v-model="areaDialogVisible" title="批量修改区号" width="400px">
      <el-form label-width="80px">
        <el-form-item label="区号">
          <el-input v-model="batchAreaNo" placeholder="请输入区号"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="areaDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitBatchAreaNo">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listLocat, addLocat, updateLocat, delLocat, batchUpdateAreaNo } from '@/api/locat'
import { listWarehouse } from '@/api/warehouse'

const loading = ref(false)
const tableData = ref([])
const warehouseList = ref([])
const selectedRows = ref([])
const searchForm = ref({ storageNo: '', locationNo: '', locationType: '', isDelete: '' })
const pagination = ref({ current: 1, size: 10 })

const loadWarehouses = async () => {
  try {
    const res = await listWarehouse({ isDelete: 0 })
    warehouseList.value = res.data
  } catch (e) { console.error(e) }
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {}
    if (searchForm.value.storageNo) params.storageNo = searchForm.value.storageNo
    if (searchForm.value.locationNo) params.locationNo = searchForm.value.locationNo
    if (searchForm.value.locationType) params.locationType = searchForm.value.locationType
    if (searchForm.value.isDelete !== '' && searchForm.value.isDelete !== null) params.isDelete = searchForm.value.isDelete
    const res = await listLocat(params)
    tableData.value = res.data
  } catch (e) { console.error(e) }
  finally { loading.value = false }
}

const pagedList = computed(() => {
  const start = (pagination.value.current - 1) * pagination.value.size
  return tableData.value.slice(start, start + pagination.value.size)
})

const handleSearch = () => { pagination.value.current = 1; loadData() }
const resetForm = () => {
  searchForm.value = { storageNo: '', locationNo: '', locationType: '', isDelete: '' }
  handleSearch()
}
const handleSelectionChange = (rows) => { selectedRows.value = rows }

const dialogVisible = ref(false)
const dialogTitle = ref('新增料位')
const form = ref({})

const handleAdd = () => {
  dialogTitle.value = '新增料位'
  form.value = { storageNo: '', locationNo: '', locationName: '', locationType: '', locationRow: '', locationColumn: '', locationLen: null, locationWidth: null, locationHeight: null, locationState: 'empty', locationAttrCode: '', dummyFlag: 0, validFlag: 1, areaNo: '', coordinateX: null, coordinateY: null, coordinateZ: null, remark: '' }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '修改料位'
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    if (form.value.id) {
      await updateLocat(form.value)
      ElMessage.success('修改成功')
    } else {
      await addLocat(form.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (e) { console.error(e) }
}

const handleDelete = (row) => {
  ElMessageBox.confirm(`确认删除料位 "${row.locationNo}" ?`, '提示', { type: 'warning' }).then(async () => {
    await delLocat(row.id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

const areaDialogVisible = ref(false)
const batchAreaNo = ref('')

const handleBatchAreaNo = () => {
  batchAreaNo.value = ''
  areaDialogVisible.value = true
}

const submitBatchAreaNo = async () => {
  if (!batchAreaNo.value) { ElMessage.warning('请输入区号'); return }
  try {
    await batchUpdateAreaNo({ ids: selectedRows.value.map(r => r.id), areaNo: batchAreaNo.value })
    ElMessage.success('批量修改成功')
    areaDialogVisible.value = false
    loadData()
  } catch (e) { console.error(e) }
}

onMounted(() => { loadWarehouses(); loadData() })
</script>

<style scoped>
.locat-container { padding: 0; flex: 1; display: flex; flex-direction: column; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 20px; }
.pagination { margin-top: 20px; display: flex; justify-content: flex-end; }
</style>
