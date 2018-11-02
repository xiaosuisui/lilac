package io.github.isliqian.sys.mapper;

import io.github.isliqian.utils.base.CrudDao;
import io.github.isliqian.sys.bean.SysArea;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("sysAreaMapper")
public interface SysAreaMapper  extends CrudDao<SysArea> {
}
