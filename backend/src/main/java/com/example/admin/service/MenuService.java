package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> listMenus();

    Menu getMenuById(Long id);

    boolean saveMenu(Menu menu);

    boolean updateMenu(Menu menu);

    boolean deleteMenu(Long id);

    List<Menu> buildMenuTree(List<Menu> menus);
}
