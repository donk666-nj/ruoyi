package com.example.admin.controller;

import com.example.admin.entity.Role;
import com.example.admin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public List<Role> listRoles() {
        return roleService.listRoles();
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping
    public boolean saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @PutMapping
    public boolean updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    @DeleteMapping("/{id}")
    public boolean deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id);
    }
}
