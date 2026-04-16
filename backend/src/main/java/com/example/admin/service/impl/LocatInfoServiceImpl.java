package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.LocatInfo;
import com.example.admin.mapper.LocatInfoMapper;
import com.example.admin.service.LocatInfoService;
import org.springframework.stereotype.Service;

@Service
public class LocatInfoServiceImpl extends ServiceImpl<LocatInfoMapper, LocatInfo> implements LocatInfoService {
}
