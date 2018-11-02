package io.github.isliqian.sys.utils;

import java.util.Date;
import java.util.List;


import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.sys.bean.*;
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

    public static final String CACHE_DICT_LIST = "dictList";
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_OFFICE_LIST = "officeList";
    public static final String CACHE_DEPARTMENT_LIST = "departmentList";
    public static final String CACHE_USER_LIST = "userList";

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
        if (entity.getClass().getName().equals( SysArea.class.getName())){
            //区域
        list = (List<T>) redisService.get(CACHE_AREA_LIST);
            if (list!=null){
                //不执行任何操作
                return list;
            }else {
                list = this.dao.findList(entity);
                redisService.set(CACHE_AREA_LIST,list);
            }

        }else if (entity.getClass().getName().equals( SysDict.class.getName())){
            //字典
            list = (List<T>) redisService.get(CACHE_DICT_LIST);
            if (list!=null){
                //不执行任何操作
                return list;
            }else {
                list = this.dao.findList(entity);
                redisService.set(CACHE_DICT_LIST,list);
            }
        }else if (entity.getClass().getName().equals(SysMenu.class.getName())){
            //菜单
            list = (List<T>) redisService.get(CACHE_MENU_LIST);
            if (list!=null){
                //不执行任何操作
                return list;
            }else {
                list = this.dao.findList(entity);
                redisService.set(CACHE_MENU_LIST,list);
            }
        }else if(entity.getClass().getName().equals(SysOffice.class.getName())){
            //机构
            list = (List<T>) redisService.get(CACHE_OFFICE_LIST);
            if (list!=null){
                //不执行任何操作
                return list;
            }else {
                list = this.dao.findList(entity);
                redisService.set(CACHE_OFFICE_LIST,list);
            }
        }else if(entity.getClass().getName().equals(SysDepartment.class.getName())){
            //部门
            list = (List<T>) redisService.get(CACHE_DEPARTMENT_LIST);
            if (list!=null){
                //不执行任何操作
                return list;
            }else {
                list = this.dao.findList(entity);
                redisService.set(CACHE_DEPARTMENT_LIST,list);
            }
        }else if (entity.getClass().getName().equals(SysRole.class.getName())){
            //角色
            list = (List<T>) redisService.get(CACHE_ROLE_LIST);
            if (list!=null){
                //不执行任何操作
                return list;
            }else {
                list = this.dao.findList(entity);
                redisService.set(CACHE_ROLE_LIST,list);
            }
        }else if (entity.getClass().getName().equals(SysUser.class.getName())){
            //用户
            list = (List<T>) redisService.get(CACHE_USER_LIST);
            if (list!=null){
                //不执行任何操作
                return list;
            }else {
                list = this.dao.findList(entity);
                redisService.set(CACHE_USER_LIST,list);
            }
        }else{
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

        common(entity);

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
        common(entity);
        entity.setDelFlag("1");
        entity.setUpdateDate(new Date());
        this.dao.delete(entity);
    }

    public void common(T entity){
        if (entity.getClass().getName().equals( SysArea.class.getName())){
            redisService.remove(CACHE_AREA_LIST);
        }else if (entity.getClass().getName().equals( SysDict.class.getName())){
            redisService.remove(CACHE_DICT_LIST);
        }else if (entity.getClass().getName().equals(SysMenu.class.getName())){
            redisService.remove(CACHE_MENU_LIST);
        }else if(entity.getClass().getName().equals(SysOffice.class.getName())){
            redisService.remove(CACHE_OFFICE_LIST);
        }else if(entity.getClass().getName().equals(SysDepartment.class.getName())){
            redisService.remove(CACHE_DEPARTMENT_LIST);

        }else if (entity.getClass().getName().equals(SysRole.class.getName())){
            redisService.remove(CACHE_ROLE_LIST);
        }else if (entity.getClass().getName().equals(SysUser.class.getName())){
            redisService.remove(CACHE_USER_LIST);
        }
    }
}
