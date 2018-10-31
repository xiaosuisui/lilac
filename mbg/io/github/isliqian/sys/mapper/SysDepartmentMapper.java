package io.github.isliqian.sys.mapper;

import io.github.isliqian.sys.bean.SysDepartment;

public interface SysDepartmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDepartment record);

    int insertSelective(SysDepartment record);

    SysDepartment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDepartment record);

    int updateByPrimaryKey(SysDepartment record);
}