package io.github.isliqian.sys.bean;

import io.github.isliqian.core.DataEntity;
import lombok.Data;


/**
 * 字典表
 */
@Data
public class SysDict extends DataEntity<SysDict> {
    private static final long serialVersionUID = 1L;
    private String value;	// 数据值
    private String label;	// 标签名
    private String type;	// 类型
    private String description;// 描述

    public SysDict() {
        super();
    }

    public SysDict(String id){
        super(id);
    }

    public SysDict(String value, String label){
        this.value = value;
        this.label = label;
    }



}
