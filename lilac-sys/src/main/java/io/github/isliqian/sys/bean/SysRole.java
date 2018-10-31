package io.github.isliqian.sys.bean;

import com.google.common.collect.Lists;
import io.github.isliqian.utils.base.DataEntity;
import lombok.Data;

import java.util.List;


/**
 * 角色表
 */
@Data
public class SysRole   extends DataEntity<SysRole> {
    private static final long serialVersionUID = 1L;
    private SysOffice office;	// 归属机构
    private String name; 	// 角色名称
    private String enname;	// 英文名称


    private String oldName; 	// 原角色名称
    private String oldEnname;	// 原英文名称
    private String sysData; 		//是否是系统数据
    private String useable; 		//是否是可用

    private SysUser user;		// 根据用户ID查询角色列表

    //	private List<User> userList = Lists.newArrayList(); // 拥有用户列表
    private List<SysMenu> menuList = Lists.newArrayList(); // 拥有菜单列表



}
