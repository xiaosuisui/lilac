package io.github.isliqian.sys.bean;


import io.github.isliqian.core.DataEntity;
import lombok.Data;


@Data
public class SysDepartment extends DataEntity<SysDepartment> {
    private static final long serialVersionUID = 1L;


    private SysOffice office;

    private String name;



}