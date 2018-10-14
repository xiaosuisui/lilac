package io.github.isliqian.controller;



import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.utils.ResultUtil;
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
//
//    @Resource
//    private ISysUserService sysUserService;
//
//    @GetMapping("/getUser")
//    @RequiresRoles("admin")
//    public ResultUtil getUser() {
//        List<SysUser> list = sysUserService.findAll();
//        return ResultUtil.success(list);
//    }
//
//    /**
//     * 封号操作
//     */
//    @PostMapping("/banUser")
//    @RequiresRoles("admin")
//    public ResultUtil updatePassword(String username) {
//        sysUserService.banUser(username);
//        return ResultUtil.success("成功封号！");
//    }
}
