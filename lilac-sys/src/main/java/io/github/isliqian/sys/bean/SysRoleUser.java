package io.github.isliqian.sys.bean;

import lombok.Data;

import javax.management.relation.Role;

/**
 * 用户角色表
 */

@Data
public class SysRoleUser {

    private String username;

    private Role role;
}
