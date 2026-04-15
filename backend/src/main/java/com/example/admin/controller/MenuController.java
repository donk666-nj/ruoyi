package com.example.admin.controller;

import com.example.admin.entity.Menu;
import com.example.admin.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<Menu> listMenus() {
        return menuService.listMenus();
    }

    @GetMapping("/tree")
    public List<Menu> getMenuTree() {
        List<Menu> menus = menuService.listMenus();
        return menuService.buildMenuTree(menus);
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @PostMapping
    public boolean saveMenu(@RequestBody Menu menu) {
        return menuService.saveMenu(menu);
    }

    @PutMapping
    public boolean updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{id}")
    public boolean deleteMenu(@PathVariable Long id) {
        return menuService.deleteMenu(id);
    }
}
