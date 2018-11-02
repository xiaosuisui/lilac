package io.github.isliqian.sys.service;

import io.github.isliqian.sys.utils.TreeService;
import io.github.isliqian.sys.bean.SysArea;
import io.github.isliqian.sys.mapper.SysAreaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service("sysAreaService")
@Transactional(readOnly = true)
public class SysAreaService extends TreeService<SysAreaMapper, SysArea> {

	public List<SysArea> findAll(){
		return dao.findAllList();
	}

	@Transactional(readOnly = false)
	public void save(SysArea area) {
		super.save(area);
//		AuthUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysArea area) {
		super.delete(area);
//		AuthUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
}
