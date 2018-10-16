/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package io.github.isliqian.sys.mapper;


import io.github.isliqian.sys.base.CrudDao;
import io.github.isliqian.sys.bean.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@Component
@Mapper
public interface SysDictMapper extends CrudDao<SysDict> {

	List<String> findTypeList(SysDict dict);

    SysDict findByType(SysDict dict);
}
