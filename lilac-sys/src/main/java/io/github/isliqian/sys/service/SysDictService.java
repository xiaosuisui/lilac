package io.github.isliqian.sys.service;


import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.sys.utils.CrudService;
import io.github.isliqian.sys.bean.SysDict;
import io.github.isliqian.sys.mapper.SysDictMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class SysDictService extends CrudService<SysDictMapper, SysDict> {

	@Resource
	private RedisService redisService;
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new SysDict());
	}

	@Transactional(readOnly = false)
	public void save(SysDict dict) {
		super.save(dict);
		//CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(SysDict dict) {
		super.delete(dict);
//		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

    public SysDict findByType(SysDict dict) {
		return dao.findByType(dict);
    }
}
