package io.github.isliqian.sys.utils;

import java.util.Date;
import java.util.List;

import io.github.isliqian.cache.bean.LilacUtil;
import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.sys.bean.SysArea;
import io.github.isliqian.sys.bean.SysDict;
import io.github.isliqian.utils.IDUtils;
import io.github.isliqian.utils.base.BaseService;
import io.github.isliqian.utils.base.CrudDao;
import io.github.isliqian.utils.base.DataEntity;
import io.github.isliqian.utils.base.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(
        readOnly = true
)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {

    public Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected D dao;

    @Resource
    private RedisService redisService;

    public CrudService() {
    }

    public T get(String id) {
        return (T) this.dao.get(id);
    }

    public T get(T entity) {
        return (T) this.dao.get(entity);
    }

    public List<T> findList(T entity) {
        List<T> list = null;
        //区域缓存
        if (entity.getClass().getName().equals( SysArea.class.getName())){
        list = (List<T>) redisService.get(LilacUtil.CACHE_AREA_LIST);
            if (list!=null){
                //不执行任何操作
                return list;
            }else {
                list = this.dao.findList(entity);
                redisService.set(LilacUtil.CACHE_AREA_LIST,list);
            }

        }else if (entity.getClass().getName().equals( SysDict.class.getName())){
            list = (List<T>) redisService.get(LilacUtil.CACHE_DICT_LIST);
            if (list!=null){
                //不执行任何操作
                return list;
            }else {
                list = this.dao.findList(entity);
                redisService.set(LilacUtil.CACHE_DICT_LIST,list);
            }
        }else {
            list = this.dao.findList(entity);
        }
        return list;
    }

    public Page<T> findPage(Page<T> page, T entity) {
        entity.setPage(page);
        page.setList(this.findList(entity));
        return page;
    }

    @Transactional(
            readOnly = false
    )
    public void save(T entity) {
        if (entity.getIsNewRecord()) {
            entity.setId(IDUtils.getId());
            entity.setCreateDate(new Date());
            entity.setUpdateDate(new Date());
            this.dao.insert(entity);
        } else {
            entity.setUpdateDate(new Date());
            this.dao.update(entity);
        }

    }

    @Transactional(
            readOnly = false
    )
    public void delete(T entity) {
        entity.setDelFlag("1");
        entity.setUpdateDate(new Date());
        this.dao.delete(entity);
    }
}
