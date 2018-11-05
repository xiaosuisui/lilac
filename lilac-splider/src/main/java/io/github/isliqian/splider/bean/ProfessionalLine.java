package io.github.isliqian.splider.bean;

import io.github.isliqian.core.DataEntity;
import lombok.Data;



/**
 * 各专业录取分数线
 */
@Data
public class ProfessionalLine extends DataEntity<ProfessionalLine> {



    private BasicCollege college;//学校

    private String section;//科目

    private String profession;//专业

    private String year;//年份



    private String highest;//最高

    private String average;//平均

    private String batch;//批次

    private String ext1;//扩展字段1

    private String ext2;//扩展字段2

    private String ext3;//扩展字段3
}
