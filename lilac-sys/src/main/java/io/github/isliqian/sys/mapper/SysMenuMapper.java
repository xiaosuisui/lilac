
package io.github.isliqian.sys.mapper;


import io.github.isliqian.core.CrudDao;
import io.github.isliqian.sys.bean.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 菜单DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@Mapper
@Component
public interface SysMenuMapper extends CrudDao<SysMenu> {

	List<SysMenu> findByParentIdsLike(SysMenu menu);

	List<SysMenu> findByUserId(SysMenu menu);
	
	int updateParentIds(SysMenu menu);
	
	int updateSort(SysMenu menu);
	
}
