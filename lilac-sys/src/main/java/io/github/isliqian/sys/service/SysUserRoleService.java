package io.github.isliqian.sys.service;

import io.github.isliqian.sys.base.CrudService;
import io.github.isliqian.sys.bean.SysUserRole;
import io.github.isliqian.sys.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SysUserRoleService extends CrudService<SysUserRoleMapper, SysUserRole> {
}
