package io.github.isliqian.sys.service;

import io.github.isliqian.core.CrudService;
import io.github.isliqian.sys.bean.SysMenu;
import io.github.isliqian.sys.mapper.SysMenuMapper;
import io.github.isliqian.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SysMenuService extends CrudService<SysMenuMapper, SysMenu> {
    public SysMenu getMenu(String id) {
        return super.dao.get(id);
    }

    public List<SysMenu> findAllMenu() {
        return super.findList(new SysMenu());
    }

    @Transactional(readOnly = false)
    public void saveMenu(SysMenu menu) {

        // 获取父节点实体
        menu.setParent(this.getMenu(menu.getParent().getId()));

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = menu.getParentIds();

        // 设置新的父节点串
        menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");

        // 保存或更新实体
        if (StringUtils.isBlank(menu.getId())) {
            super.dao.insert(menu);
        } else {
            super.dao.update(menu);
        }

        // 更新子节点 parentIds
        SysMenu m = new SysMenu();
        m.setParentIds("%," + menu.getId() + ",%");
        List<SysMenu> list = super.dao.findByParentIdsLike(m);
        for (SysMenu e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
            super.dao.updateParentIds(e);
        }
        // 清除用户菜单缓存
        //AuthUtils.removeCache(UserUtils.CACHE_MENU_LIST);
        // 清除日志相关缓存
       // CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
    }

    @Transactional(readOnly = false)
    public void updateMenuSort(SysMenu menu) {
        super.dao.updateSort(menu);
        // 清除用户菜单缓存
        //AuthUtils.removeCache(UserUtils.CACHE_MENU_LIST);
        // 清除日志相关缓存
        //CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
    }

    @Transactional(readOnly = false)
    public void deleteMenu(SysMenu menu) {
        super.dao.delete(menu);
        // 清除用户菜单缓存
        //AuthUtils.removeCache(UserUtils.CACHE_MENU_LIST);
        // 清除日志相关缓存
        //CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
    }

}
