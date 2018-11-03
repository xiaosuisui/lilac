package io.github.isliqian.sys.mapper;

import io.github.isliqian.core.CrudDao;
import io.github.isliqian.sys.bean.SysOffice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
public interface SysOfficeMapper extends CrudDao<SysOffice> {



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


}
