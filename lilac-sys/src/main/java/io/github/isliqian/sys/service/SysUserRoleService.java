package io.github.isliqian.sys.service;

import io.github.isliqian.core.CrudService;
import io.github.isliqian.sys.bean.SysUserRole;
import io.github.isliqian.sys.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SysUserRoleService extends CrudService<SysUserRoleMapper, SysUserRole> {

    /**
     * 根据用户id查询该用户所属角色
     * @param id
     * @return
     */
    public List<SysUserRole> getRoles(String id) {
        return super.dao.getRoles(id);
    }
}
