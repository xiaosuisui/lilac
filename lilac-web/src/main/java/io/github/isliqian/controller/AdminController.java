package io.github.isliqian.controller;



import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.utils.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * admin角色权限controller
 */
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {



    @GetMapping("/role")
    @RequiresRoles("admin")
    public ResultUtil roleTest() {

        return ResultUtil.success("admin角色才能看到");
    }
    @GetMapping("/pe")
    @RequiresPermissions("sys:dict:view")
    public ResultUtil getUser() {

        return ResultUtil.success("sys:dict:view");
    }

}
