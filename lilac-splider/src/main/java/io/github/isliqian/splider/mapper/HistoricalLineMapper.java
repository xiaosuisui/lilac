package io.github.isliqian.splider.mapper;

import io.github.isliqian.core.CrudDao;
import io.github.isliqian.core.DataEntity;
import io.github.isliqian.splider.bean.HistoricalLine;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 高考历年分数线
 */
@Mapper
@Component
public interface HistoricalLineMapper extends CrudDao<HistoricalLine> {



}
