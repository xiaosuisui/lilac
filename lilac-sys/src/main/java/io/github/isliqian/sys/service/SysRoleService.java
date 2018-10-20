package io.github.isliqian.sys.service;

import io.github.isliqian.shiro.utils.AuthUtils;
import io.github.isliqian.shiro.utils.UserUtils;
import io.github.isliqian.sys.base.CrudService;
import io.github.isliqian.sys.bean.SysRole;
import io.github.isliqian.sys.mapper.SysRoleMapper;
import io.github.isliqian.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SysRoleService extends CrudService<SysRoleMapper, SysRole> {

    public SysRole getRole(String id) {
        return super.dao.get(id);
    }

    public SysRole getRoleByName(String name) {
        SysRole r = new SysRole();
        r.setName(name);
        return super.dao.getByName(r);
    }

    public SysRole getRoleByEnname(String enname) {
        SysRole r = new SysRole();
        r.setEnname(enname);
        return super.dao.getByEnname(r);
    }

    public List<SysRole> findRole(SysRole role) {
        return super.dao.findList(role);
    }

    public List<SysRole> findAllRole() {
        return super.dao.findAllList();
    }

    @Transactional(readOnly = false)
    public void saveRole(SysRole role) {
        if (StringUtils.isBlank(role.getId())) {
            role.preInsert();
            super.dao.insert(role);
        } else {
            role.preUpdate();
            super.dao.update(role);
        }
        // 更新角色与菜单关联
        super.dao.deleteRoleMenu(role);
        if (role.getMenuList().size() > 0) {
            super.dao.insertRoleMenu(role);
        }
//        // 更新角色与部门关联
//        super.dao.deleteRoleOffice(role);
//        if (role.getOfficeList().size() > 0) {
//            super.dao.insertRoleOffice(role);
//        }
        // 清除用户角色缓存
        //AuthUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
    }

    @Transactional(readOnly = false)
    public void deleteRole(SysRole role) {
        super.dao.delete(role);
        // 清除用户角色缓存
        //AuthUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
    }




}
