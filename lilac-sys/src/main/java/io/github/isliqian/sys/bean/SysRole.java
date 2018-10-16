package io.github.isliqian.sys.bean;

import io.github.isliqian.sys.base.DataEntity;
import lombok.Data;



/**
 * 角色表
 */
@Data
public class SysRole   extends DataEntity<SysRole> {

    private String id;

    private String name;

    @Override
    public void doPreInsert() {

    }

    @Override
    public void doPreUpdate() {

    }
}
