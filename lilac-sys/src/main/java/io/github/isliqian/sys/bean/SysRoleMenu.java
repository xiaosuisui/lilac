package io.github.isliqian.sys.bean;

import io.github.isliqian.sys.base.DataEntity;
import lombok.Data;

/**
 * 角色菜单表
 */
@Data
public class SysRoleMenu extends DataEntity<SysRoleMenu> {

    private SysRole role;
    private SysMenu menu;


    @Override
    public void doPreInsert() {
    }

    @Override
    public void doPreUpdate() {
    }
}