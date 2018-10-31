package io.github.isliqian.sys.mapper;

import io.github.isliqian.sys.bean.SysDepartment;
import io.github.isliqian.utils.base.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SysDepartmentMapper extends CrudDao<SysDepartment> {

}