package io.github.isliqian.sys.bean;

import io.github.isliqian.core.DataEntity;
import lombok.Data;

/**
 * 角色菜单表
 */
@Data
public class SysRoleMenu extends DataEntity<SysRoleMenu> {

    private SysRole role;
    private SysMenu menu;



}
