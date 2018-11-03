package io.github.isliqian.sys.bean;

import io.github.isliqian.core.DataEntity;
import lombok.Data;

/**
 * 用户角色表
 */

@Data
public class SysUserRole extends DataEntity<SysUserRole> {

    private SysUser user;

    private SysRole role;


}
