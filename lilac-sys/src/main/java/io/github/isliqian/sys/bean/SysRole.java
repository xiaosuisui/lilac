package io.github.isliqian.sys.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 */
@Data
public class SysRole  implements Serializable {

    private String id;

    private String name;
}
