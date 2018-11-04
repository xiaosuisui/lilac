package io.github.isliqian.sys.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.isliqian.core.DataEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * 菜单表
 */
@Data
public class SysMenu extends DataEntity<SysMenu> {

private static final long serialVersionUID = 1L;
private SysMenu parent;	// 父级菜单
private String parentIds; // 所有父级编号
private String name; 	// 名称
private String href; 	// 链接
private String target; 	// 目标（ mainFrame、_blank、_self、_parent、_top）
private String icon; 	// 图标
private Integer sort; 	// 排序
private String isShow; 	// 是否在菜单中显示（1：显示；0：不显示）
private String permission; // 权限标识



public SysMenu(){
        super();
        this.sort = 30;
        this.isShow = "1";
        }

public SysMenu(String id){
        super(id);
        }





}
