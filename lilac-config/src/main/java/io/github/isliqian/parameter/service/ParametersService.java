package io.github.isliqian.parameter.service;


import io.github.isliqian.parameter.entity.ParametersBean;
import io.github.isliqian.parameter.mapper.ParametersMapper;
import io.github.isliqian.utils.base.CrudService;
import io.github.isliqian.utils.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = false)
public class ParametersService extends CrudService<ParametersMapper, ParametersBean> implements InitializingBean {
    private static Map<String, String> map = new HashMap<String, String>();

    /**
     * 初始化时 将数据库中的配置参数 加载到内存
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ParametersBean parametersBean=new ParametersBean();
        parametersBean.setDelFlag("0");
        List<ParametersBean> list = dao.findList(parametersBean);
        for (ParametersBean p : list) {
            map.put(p.getBond(), p.getValue());
        }
    }

    public String getStringByKey(String key) {
        return map.get(key);
    }

    public Integer getIntByKey(String key) {
        return Integer.valueOf(map.get(key));
    }
    public Double getDoubleByKey(String key){return Double.valueOf(map.get(key));}

    public Date getDateByKey(String key, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = fmt.parse(map.get(key));
        } catch (ParseException e) {
            logger.error("字符串转日期失败", e);
        }
        return date;
    }

    public void setPataByKey(String key, String value) {
        map.put(key, value);
    }

    public List<ParametersBean> getPataByType(String type) {
        List<ParametersBean> list = dao.getpataByType(type);
        for(ParametersBean b:list){
            if("Radio".equals(b.getDisplayType()) || "Checkbox".equals(b.getDisplayType())){
                String vr = b.getValueRange();
                if(StringUtils.isNotBlank(vr)){
                    Map<String,String> map = new HashMap<String,String>();
                    for(String m : vr.split(",")){
                        String[] kv=m.split(":");
                        map.put(kv[0],kv[1]);
                    }
                    b.setValueRangeMap(map);
                }
            }
        }
        return list;
    }
    public void updateParaById(ParametersBean parametersBean){
        dao.updatePataById(parametersBean);
    }
    public void updateparaByKey(ParametersBean parametersBean){
        dao.updatePataByBond(parametersBean);
    }
    public List<String> getTypes(){return dao.getTypes();}


    /**
     * 批量修改参数排序
     */
    public void updateSort(ParametersBean bean){
        dao.updateSort(bean);
    }

}
