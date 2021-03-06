package io.github.isliqian.splider.bean;

import io.github.isliqian.core.DataEntity;
import lombok.Data;

/**
 * 高考历年分数线
 */
@Data
public class HistoricalLine extends DataEntity<HistoricalLine> {



    private College college;//学校

    private String section;//科目

    private String city;
    private String year;//年份

    private String highest;//最高

    private String lowest;//最低

    private String average;//平均

    private String batch;//批次

    private String ext1;//扩展字段1

    private String ext2;//扩展字段2

    private String ext3;//扩展字段3

}
