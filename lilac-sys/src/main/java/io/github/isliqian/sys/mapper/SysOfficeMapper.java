package io.github.isliqian.sys.mapper;

import io.github.isliqian.sys.base.CrudDao;
import io.github.isliqian.sys.base.TreeDao;
import io.github.isliqian.sys.bean.SysOffice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface SysOfficeMapper extends TreeDao<SysOffice> {

    /**
     * 根据起始更新时间查询
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     * @author wxt.touxin
     */
    List<SysOffice> findListByUpdateDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 通过area_id 和 type=2 进行筛选
     * @param office
     * @return
     */
    List<SysOffice> findListByArea(SysOffice office);

    /**
     * 通过isauto_fee 进行筛选
     * @param office
     * @return
     */
    List<SysOffice> findIsAutoList(SysOffice office);

    /**
     * 通过获取子节点
     * @param office
     * @return
     */
    List<SysOffice> getChildren(SysOffice office);
}
