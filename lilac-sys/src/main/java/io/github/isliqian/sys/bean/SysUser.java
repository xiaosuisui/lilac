package io.github.isliqian.sys.bean;

import io.github.isliqian.sys.base.DataEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by LiQian_Nice on 2018/6/9
 */
@Data
public class SysUser extends DataEntity<SysUser> {
    private static final long serialVersionUID = 1L;
    private SysOffice office;    // 归属部门
    private String loginName;// 登录名
    private String password;// 密码
    private String no;        // 工号
    private String name;    // 姓名
    private String email;    // 邮箱
    private String phone;    // 电话
    private String mobile;    // 手机
    private String userType;// 用户类型
    private String loginIp;    // 最后登陆IP
    private Date loginDate;    // 最后登陆日期
    private String loginFlag;    // 是否允许登陆
    private String photo;    // 头像

    private String oldLoginName;// 原登录名
    private String newPassword;    // 新密码

    private String oldLoginIp;    // 上次登陆IP
    private Date oldLoginDate;    // 上次登陆日期

    private SysRole role;    // 根据角色查询用户条件
    protected SysUser createBy;	// 创建者
    protected SysUser updateBy;	// 更新者


    @Override
    public void doPreInsert() {

    }

    @Override
    public void doPreUpdate() {

    }
}
