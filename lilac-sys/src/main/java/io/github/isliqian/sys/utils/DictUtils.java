package io.github.isliqian.sys.utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.isliqian.sys.bean.SysDict;
import io.github.isliqian.sys.mapper.SysDictMapper;
import org.apache.commons.lang3.StringUtils;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {

    private static SysDictMapper dictDao = MicAppContext.getBean(SysDictMapper.class);

    public static final String CACHE_DICT_MAP = "dictMap";

    public static String getDictLabel(String value, String type, String defaultValue){
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
            for (SysDict dict : getDictList(type)){
                if (type.equals(dict.getType()) && value.equals(dict.getValue())){
                    return dict.getLabel();
                }
            }
        }
        return defaultValue;
    }

    public static String getDictLabels(String values, String type, String defaultValue){
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
            List<String> valueList = Lists.newArrayList();
            for (String value : StringUtils.split(values, ",")){
                valueList.add(getDictLabel(value, type, defaultValue));
            }
            return StringUtils.join(valueList, ",");
        }
        return defaultValue;
    }

    public static String getDictValue(String label, String type, String defaultLabel){
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
            for (SysDict dict : getDictList(type)){
                if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
                    return dict.getValue();
                }
            }
        }
        return defaultLabel;
    }

    public static List<SysDict> getDictList(String type){
        @SuppressWarnings("unchecked")
        Map<String, List<SysDict>> dictMap = (Map<String, List<SysDict>>)CacheUtils.get(CACHE_DICT_MAP);
        if (dictMap==null){
            dictMap = Maps.newHashMap();
            for (SysDict dict : dictDao.findAllList(new SysDict())){
                List<SysDict> dictList = dictMap.get(dict.getType());
                if (dictList != null){
                    dictList.add(dict);
                }else{
                    dictMap.put(dict.getType(), Lists.newArrayList(dict));
                }
            }
            CacheUtils.put(CACHE_DICT_MAP, dictMap);
        }
        List<SysDict> dictList = dictMap.get(type);
        if (dictList == null){
            dictList = Lists.newArrayList();
        }
        return dictList;
    }

    /**
     * 返回字典列表（JSON）
     * @param type
     * @return
     */
    public static String getDictListJson(String type){
        return JsonUtils.toJsonString(getDictList(type));
    }

}