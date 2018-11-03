package io.github.isliqian.parameter.mapper;


import io.github.isliqian.parameter.entity.ParametersBean;
import io.github.isliqian.core.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ParametersMapper extends CrudDao<ParametersBean> {
    ParametersBean getPataByBond(String bond);
    void updatePataById(ParametersBean para);
    void updatePataByBond(ParametersBean parametersBean);
    List<ParametersBean> getpataByType(String type);
    List<String> getTypes();
    int updateSort(ParametersBean bean);
}
