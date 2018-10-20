package io.github.isliqian.sys.mapper;

import io.github.isliqian.sys.base.CrudDao;
import io.github.isliqian.sys.bean.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 角色菜单表
 */
@Mapper
@Component
public interface SysRoleMenuMapper extends CrudDao<SysRoleMenu> {


}
