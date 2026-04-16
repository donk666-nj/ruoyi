package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("MMWM_LOCAT_INFO")
public class LocatInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String storageNo;
    private String locationNo;
    private String locationName;
    private String locationType;
    private String locationRow;
    private String locationColumn;
    private BigDecimal locationLen;
    private BigDecimal locationWidth;
    private BigDecimal locationHeight;
    private String locationState;
    private String locationAttrCode;
    private Integer dummyFlag;
    private Integer validFlag;
    private String areaNo;
    private String rackNo;
    private BigDecimal coordinateX;
    private BigDecimal coordinateY;
    private BigDecimal coordinateZ;
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
    public String getLocationNo() { return locationNo; }
    public void setLocationNo(String locationNo) { this.locationNo = locationNo; }
    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public String getLocationType() { return locationType; }
    public void setLocationType(String locationType) { this.locationType = locationType; }
    public String getLocationRow() { return locationRow; }
    public void setLocationRow(String locationRow) { this.locationRow = locationRow; }
    public String getLocationColumn() { return locationColumn; }
    public void setLocationColumn(String locationColumn) { this.locationColumn = locationColumn; }
    public BigDecimal getLocationLen() { return locationLen; }
    public void setLocationLen(BigDecimal locationLen) { this.locationLen = locationLen; }
    public BigDecimal getLocationWidth() { return locationWidth; }
    public void setLocationWidth(BigDecimal locationWidth) { this.locationWidth = locationWidth; }
    public BigDecimal getLocationHeight() { return locationHeight; }
    public void setLocationHeight(BigDecimal locationHeight) { this.locationHeight = locationHeight; }
    public String getLocationState() { return locationState; }
    public void setLocationState(String locationState) { this.locationState = locationState; }
    public String getLocationAttrCode() { return locationAttrCode; }
    public void setLocationAttrCode(String locationAttrCode) { this.locationAttrCode = locationAttrCode; }
    public Integer getDummyFlag() { return dummyFlag; }
    public void setDummyFlag(Integer dummyFlag) { this.dummyFlag = dummyFlag; }
    public Integer getValidFlag() { return validFlag; }
    public void setValidFlag(Integer validFlag) { this.validFlag = validFlag; }
    public String getAreaNo() { return areaNo; }
    public void setAreaNo(String areaNo) { this.areaNo = areaNo; }
    public String getRackNo() { return rackNo; }
    public void setRackNo(String rackNo) { this.rackNo = rackNo; }
    public BigDecimal getCoordinateX() { return coordinateX; }
    public void setCoordinateX(BigDecimal coordinateX) { this.coordinateX = coordinateX; }
    public BigDecimal getCoordinateY() { return coordinateY; }
    public void setCoordinateY(BigDecimal coordinateY) { this.coordinateY = coordinateY; }
    public BigDecimal getCoordinateZ() { return coordinateZ; }
    public void setCoordinateZ(BigDecimal coordinateZ) { this.coordinateZ = coordinateZ; }
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
