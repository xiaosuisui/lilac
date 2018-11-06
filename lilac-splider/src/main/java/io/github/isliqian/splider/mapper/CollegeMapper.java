package io.github.isliqian.splider.mapper;

import io.github.isliqian.core.CrudDao;
import io.github.isliqian.splider.bean.BasicCollege;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author wxt.liqian
 * @version 2018/11/3
 * @desc
 */
@Mapper
@Component
public interface CollegeMapper  extends CrudDao<BasicCollege> {

}
