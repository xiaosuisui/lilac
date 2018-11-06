package io.github.isliqian.splider.mapper;

import io.github.isliqian.core.CrudDao;

import io.github.isliqian.splider.bean.ProfessionalLine;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


/**
 * 各专业录取分数线
 */
@Mapper
@Component
public interface ProfessionalLineMapper extends CrudDao<ProfessionalLine> {


}
