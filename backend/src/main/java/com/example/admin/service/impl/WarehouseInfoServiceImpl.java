package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.WarehouseInfo;
import com.example.admin.mapper.WarehouseInfoMapper;
import com.example.admin.service.WarehouseInfoService;
import org.springframework.stereotype.Service;

@Service
public class WarehouseInfoServiceImpl extends ServiceImpl<WarehouseInfoMapper, WarehouseInfo> implements WarehouseInfoService {
}
