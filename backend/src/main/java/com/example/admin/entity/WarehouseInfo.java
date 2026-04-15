package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

@TableName("warehouse_info")
public class WarehouseInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String storageNo;
    private String storageName;
    private String custName;
    private String storageTypeNo;
    private String storageAttr;
    private Integer dummyFlag;
    private Integer validFlag;
    private String storageAddrNo;
    private String storageAddrName;
    private Integer isDelete;
    private String createBy;
    private LocalDateTime createTime;
    private String updateBy;
    private LocalDateTime updateTime;
    private String remark;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStorageNo() { return storageNo; }
    public void setStorageNo(String storageNo) { this.storageNo = storageNo; }
    public String getStorageName() { return storageName; }
    public void setStorageName(String storageName) { this.storageName = storageName; }
    public String getCustName() { return custName; }
    public void setCustName(String custName) { this.custName = custName; }
    public String getStorageTypeNo() { return storageTypeNo; }
    public void setStorageTypeNo(String storageTypeNo) { this.storageTypeNo = storageTypeNo; }
    public String getStorageAttr() { return storageAttr; }
    public void setStorageAttr(String storageAttr) { this.storageAttr = storageAttr; }
    public Integer getDummyFlag() { return dummyFlag; }
    public void setDummyFlag(Integer dummyFlag) { this.dummyFlag = dummyFlag; }
    public Integer getValidFlag() { return validFlag; }
    public void setValidFlag(Integer validFlag) { this.validFlag = validFlag; }
    public String getStorageAddrNo() { return storageAddrNo; }
    public void setStorageAddrNo(String storageAddrNo) { this.storageAddrNo = storageAddrNo; }
    public String getStorageAddrName() { return storageAddrName; }
    public void setStorageAddrName(String storageAddrName) { this.storageAddrName = storageAddrName; }
    public Integer getIsDelete() { return isDelete; }
    public void setIsDelete(Integer isDelete) { this.isDelete = isDelete; }
    public String getCreateBy() { return createBy; }
    public void setCreateBy(String createBy) { this.createBy = createBy; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
