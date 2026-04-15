package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.admin.entity.WarehouseInfo;
import com.example.admin.service.WarehouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseInfoController {
    @Autowired
    private WarehouseInfoService warehouseInfoService;

    @GetMapping("/list")
    public List<WarehouseInfo> list(
            @RequestParam(required = false) String storageNo,
            @RequestParam(required = false) String storageName,
            @RequestParam(required = false) String storageTypeNo,
            @RequestParam(required = false) String storageAttr,
            @RequestParam(required = false) Integer isDelete) {
        LambdaQueryWrapper<WarehouseInfo> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(storageNo)) wrapper.like(WarehouseInfo::getStorageNo, storageNo);
        if (StringUtils.hasText(storageName)) wrapper.like(WarehouseInfo::getStorageName, storageName);
        if (StringUtils.hasText(storageTypeNo)) wrapper.eq(WarehouseInfo::getStorageTypeNo, storageTypeNo);
        if (StringUtils.hasText(storageAttr)) wrapper.eq(WarehouseInfo::getStorageAttr, storageAttr);
        if (isDelete != null) wrapper.eq(WarehouseInfo::getIsDelete, isDelete);
        return warehouseInfoService.list(wrapper);
    }

    @PostMapping
    public boolean save(@RequestBody WarehouseInfo info) {
        return warehouseInfoService.save(info);
    }

    @PutMapping
    public boolean update(@RequestBody WarehouseInfo info) {
        return warehouseInfoService.updateById(info);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        WarehouseInfo info = new WarehouseInfo();
        info.setId(id);
        info.setIsDelete(1);
        return warehouseInfoService.updateById(info);
    }
}
