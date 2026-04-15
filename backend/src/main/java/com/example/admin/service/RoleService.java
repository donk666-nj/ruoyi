package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> listRoles();

    Role getRoleById(Long id);

    boolean saveRole(Role role);

    boolean updateRole(Role role);

    boolean deleteRole(Long id);
}
