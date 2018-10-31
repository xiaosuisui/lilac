package io.github.isliqian.sys.service;

import io.github.isliqian.utils.base.CrudService;
import io.github.isliqian.utils.base.Page;
import io.github.isliqian.utils.base.ServiceException;
import io.github.isliqian.sys.bean.SysOffice;
import io.github.isliqian.sys.mapper.SysOfficeMapper;
import io.github.isliqian.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SysOfficeService extends CrudService<SysOfficeMapper, SysOffice> {

    public List<SysOffice> findAll(){
        return super.dao.findAllList();
    }



    public SysOffice get(String id){
        return super.get(id);
    }

    @Transactional(readOnly = true)
    public List<SysOffice> findList(SysOffice office){
        return dao.findList(new SysOffice());
    }

    @Transactional(readOnly = true)
    public List<SysOffice> findSimpleList(SysOffice office){
        return dao.findList(office);
    }

    @Transactional(readOnly = false)
    public void save(SysOffice office) {
        super.save(office);
//        AuthUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

    @Transactional(readOnly = false)
    public void delete(SysOffice office) {
        super.delete(office);
//        AuthUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

    /**
     * <p>批量保存。</p>
     * <p>如果Office的Id存在，则更新该Office；如果Office的id不存在，则新增该Office。</p>
     * <p>SQLSERVER数据库可能存在参数数量限制，如遇此情况，请将list按照合适的参数数量分段后调用此方法提交。分段提交时，调用方法注意控制事务。</p>
     * @param offices
     */
    @Transactional(readOnly = false)
    public void saveList(List<SysOffice> offices) {
        if (offices == null){
            throw new ServiceException("parameter is null!");
        }
        if(offices.isEmpty()){
            return;
        }
        List<SysOffice> inserts = new ArrayList<>();
        List<SysOffice> updates = new ArrayList<>();
        for (SysOffice office : offices){
            if (StringUtils.isBlank(office.getId())){
                inserts.add(office);
            } else {
                updates.add(office);
            }
        }

        if (!inserts.isEmpty()){
            dao.insertList(inserts);
        }

        if (!updates.isEmpty()){
            dao.updateList(updates);
        }
    }

    @Transactional(readOnly = false)
    public void deleteList(List<SysOffice> offices) {
        if (offices == null){
            throw new ServiceException("parameter is null!");
        }
        if(offices.isEmpty()){
            return;
        }
        dao.deleteList(offices);
    }



    /**
     * 通过area_id 和 type=2 进行筛选
     * @param office
     * @return
     */
    public Page<SysOffice> findPageByArea(Page<SysOffice> page, SysOffice office){
        office.setPage(page);
        page.setList(dao.findListByArea(office));
        return page;
    }


}
