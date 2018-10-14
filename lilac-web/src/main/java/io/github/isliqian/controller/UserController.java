package io.github.isliqian.controller;

import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.log.ann.MyLog;


import io.github.isliqian.sys.service.SysUserService;
import io.github.isliqian.utils.PasswordUtils;
import io.github.isliqian.utils.ResultUtil;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/user")
@Api(value = "UserController|用户接口")
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/")
    @MyLog(value = "获取全部用户")
    @ApiOperation(value="获取全部用户", notes="test: 仅1和2有正确返回")
    @RequiresRoles("admin")
    public ResultUtil findAll(){
        return ResultUtil.success(sysUserService.findAll());
    }

    @PostMapping("/")
    @ApiOperation(value = "用户注册",notes = "用户注册接口")
    public ResultUtil save(@Valid SysUser sysUser){
        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(sysUser.getNewPassword())) {
            sysUser.setPassword(PasswordUtils.entryptPassword(sysUser.getNewPassword()));
        }
        return ResultUtil.success(sysUser);
    }



    /**
     * 拥有 user, admin 角色的用户可以访问下面的页面
     */
    @GetMapping("/getMessage")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public ResultUtil getMessage() {
        return ResultUtil.success("成功获得信息！");
    }

    @PostMapping("/updatePassword")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public ResultUtil updatePassword(String username, String oldPassword, String newPassword) {
        SysUser dataBasePassword = sysUserService.getByLoginName(username);
        if (dataBasePassword.equals(oldPassword)) {
            sysUserService.updatePassword(username, newPassword);
        } else {
            return ResultUtil.error(201,"密码错误！");
        }
        return ResultUtil.success("成功获得信息！");
    }

    /**
     * 拥有 vip 权限可以访问该页面
     */
    @GetMapping("/getVipMessage")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    @RequiresPermissions("vip")
    public ResultUtil getVipMessage() {
        return ResultUtil.success("成功获得 vip 信息！");
    }
}
