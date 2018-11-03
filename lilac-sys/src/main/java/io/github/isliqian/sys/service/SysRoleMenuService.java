package io.github.isliqian.sys.service;

import io.github.isliqian.core.CrudService;
import io.github.isliqian.sys.bean.SysRoleMenu;
import io.github.isliqian.sys.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SysRoleMenuService extends CrudService<SysRoleMenuMapper, SysRoleMenu> {

}
