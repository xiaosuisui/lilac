package io.github.isliqian.sys.mapper;



import io.github.isliqian.sys.base.CrudDao;
import io.github.isliqian.sys.bean.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 系统角色
 */
@Component
@Mapper
public interface SysRoleMapper extends CrudDao<SysRole> {

}
