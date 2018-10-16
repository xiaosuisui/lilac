package io.github.isliqian.sys.service;

import io.github.isliqian.sys.base.CrudService;
import io.github.isliqian.sys.bean.SysRole;
import io.github.isliqian.sys.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SysRoleService extends CrudService<SysRoleMapper, SysRole> {
}
