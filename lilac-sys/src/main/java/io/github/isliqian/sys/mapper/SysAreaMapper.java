package io.github.isliqian.sys.mapper;

import io.github.isliqian.utils.base.TreeDao;
import io.github.isliqian.sys.bean.SysArea;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysAreaMapper  extends TreeDao<SysArea> {
}
