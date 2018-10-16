package io.github.isliqian.sys.service;

import io.github.isliqian.sys.base.CrudService;
import io.github.isliqian.sys.bean.SysMenu;
import io.github.isliqian.sys.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SysMenuService extends CrudService<SysMenuMapper, SysMenu> {

}
