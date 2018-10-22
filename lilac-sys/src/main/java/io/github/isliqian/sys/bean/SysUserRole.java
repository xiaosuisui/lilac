package io.github.isliqian.sys.bean;

import io.github.isliqian.sys.base.DataEntity;
import lombok.Data;

import javax.management.relation.Role;

/**
 * 用户角色表
 */

@Data
public class SysUserRole extends DataEntity<SysUserRole> {

    private SysUser user;

    private SysRole role;


    @Override
    public void doPreInsert() {

    }

    @Override
    public void doPreUpdate() {

    }
}
