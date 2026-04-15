package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.Menu;
import com.example.admin.mapper.MenuMapper;
import com.example.admin.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    public List<Menu> listMenus() {
        return list();
    }

    @Override
    public Menu getMenuById(Long id) {
        return getById(id);
    }

    @Override
    public boolean saveMenu(Menu menu) {
        return save(menu);
    }

    @Override
    public boolean updateMenu(Menu menu) {
        return updateById(menu);
    }

    @Override
    public boolean deleteMenu(Long id) {
        return removeById(id);
    }

    @Override
    public List<Menu> buildMenuTree(List<Menu> menus) {
        List<Menu> treeMenus = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getParentId() != null && menu.getParentId() == 0L) {
                treeMenus.add(buildMenu(menu, menus));
            }
        }
        return treeMenus;
    }

    private Menu buildMenu(Menu menu, List<Menu> menus) {
        List<Menu> children = new ArrayList<>();
        for (Menu child : menus) {
            if (child.getParentId().equals(menu.getId())) {
                children.add(buildMenu(child, menus));
            }
        }
        menu.setChildren(children);
        return menu;
    }
}
