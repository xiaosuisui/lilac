package io.github.isliqian.sys.bean;

import io.github.isliqian.sys.base.DataEntity;
import lombok.Data;


/**
 * Created by LiQian_Nice on 2018/6/9
 */
@Data
public class SysUser extends DataEntity<SysUser> {
    private static final long serialVersionUID = 1L;
    private SysOffice office;    // 归属部门
    private SysRole role;// 用户类型
    private SysArea area;//用户区域
    private String loginName;// 登录名
    private String password;// 密码
    private String no;        // 工号
    private String name;    // 姓名
    private String email;    // 邮箱
    private String phone;    // 电话
    private String mobile;    // 手机

    private String loginFlag;    // 是否允许登陆
    private String photo;    // 头像



    //private List<SysRole> roleList; //一个用户可能有多个角色



}
