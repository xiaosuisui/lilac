package io.github.isliqian.sys.bean;

import io.github.isliqian.sys.base.DataEntity;
import lombok.Data;

import javax.management.relation.Role;

/**
 * 用户角色表
 */

@Data
public class SysUserRole extends DataEntity<SysUserRole> {

    private String username;

    private Role role;


    @Override
    public void doPreInsert() {

    }

    @Override
    public void doPreUpdate() {

    }
}
