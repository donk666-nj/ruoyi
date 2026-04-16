package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.admin.entity.LocatInfo;
import com.example.admin.service.LocatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locat")
public class LocatInfoController {
    @Autowired
    private LocatInfoService locatInfoService;

    @GetMapping("/list")
    public List<LocatInfo> list(
            @RequestParam(required = false) String storageNo,
            @RequestParam(required = false) String locationNo,
            @RequestParam(required = false) String locationType,
            @RequestParam(required = false) Integer isDelete) {
        LambdaQueryWrapper<LocatInfo> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(storageNo)) wrapper.eq(LocatInfo::getStorageNo, storageNo);
        if (StringUtils.hasText(locationNo)) wrapper.like(LocatInfo::getLocationNo, locationNo);
        if (StringUtils.hasText(locationType)) wrapper.eq(LocatInfo::getLocationType, locationType);
        if (isDelete != null) wrapper.eq(LocatInfo::getIsDelete, isDelete);
        return locatInfoService.list(wrapper);
    }

    @PostMapping
    public boolean save(@RequestBody LocatInfo info) {
        return locatInfoService.save(info);
    }

    @PutMapping
    public boolean update(@RequestBody LocatInfo info) {
        return locatInfoService.updateById(info);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        LocatInfo info = new LocatInfo();
        info.setId(id);
        info.setIsDelete(1);
        return locatInfoService.updateById(info);
    }

    @PutMapping("/updateAreaNo")
    public boolean updateAreaNo(@RequestBody Map<String, Object> params) {
        List<Long> ids = (List<Long>) params.get("ids");
        String areaNo = (String) params.get("areaNo");
        if (ids == null || ids.isEmpty() || !StringUtils.hasText(areaNo)) return false;
        for (Long id : ids) {
            LocatInfo info = new LocatInfo();
            info.setId(id);
            info.setAreaNo(areaNo);
            locatInfoService.updateById(info);
        }
        return true;
    }
}
