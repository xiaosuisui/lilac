package io.github.isliqian.sys.service;

import io.github.isliqian.sys.bean.SysDepartment;
import io.github.isliqian.sys.mapper.SysDepartmentMapper;
import io.github.isliqian.utils.StringUtils;
import io.github.isliqian.sys.utils.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class SysDepartmentService extends CrudService<SysDepartmentMapper, SysDepartment> {

    public SysDepartment getDepartment(String id) {
        return super.dao.get(id);
    }



    public List<SysDepartment> findDepartments(SysDepartment sysDepartment) {
        return super.dao.findList(sysDepartment);
    }

    public List<SysDepartment> findAllDepartments() {
        return super.dao.findAllList();
    }

    @Transactional(readOnly = false)
    public void saveDepartment(SysDepartment sysDepartment) {
        if (StringUtils.isBlank(sysDepartment.getId())) {
            super.dao.insert(sysDepartment);
        } else {
            super.dao.update(sysDepartment);
        }
    }

    @Transactional(readOnly = false)
    public void deleteSysDepartment(SysDepartment sysDepartment) {
        super.dao.delete(sysDepartment);
        // 清除用户角色缓存
        //AuthUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
    }
}
