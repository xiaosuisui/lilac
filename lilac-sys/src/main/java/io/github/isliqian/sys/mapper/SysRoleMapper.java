package io.github.isliqian.sys.mapper;



import io.github.isliqian.sys.base.CrudDao;
import io.github.isliqian.sys.bean.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 系统角色
 */
@Component
@Mapper
public interface SysRoleMapper extends CrudDao<SysRole> {
    SysRole getByName(SysRole role);

    SysRole getByEnname(SysRole role);

    /**
     * 维护角色与菜单权限关系
     * @param role
     * @return
     */
    int deleteRoleMenu(SysRole role);

    int insertRoleMenu(SysRole role);

    /**
     * 维护角色与公司部门关系
     * @param role
     * @return
     */
    int deleteRoleOffice(SysRole role);

    int insertRoleOffice(SysRole role);
}
