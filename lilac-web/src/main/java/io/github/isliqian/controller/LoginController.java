package io.github.isliqian.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.shiro.utils.JWTUtil;
import io.github.isliqian.sys.service.SysUserService;
import io.github.isliqian.utils.PasswordUtils;
import io.github.isliqian.utils.ResultUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin
public class LoginController {
    @Resource
    private SysUserService sysUserService;

    @MyLog(value = "用户登录")
    @PostMapping("/login")
    public ResultUtil login(@RequestBody  SysUser user) {

            SysUser sysUser = sysUserService.getByLoginName(user.getLoginName());
            if (sysUser == null) {
                return ResultUtil.error(401, "用户名错误");
            } else if (!PasswordUtils.validatePassword(user.getPassword(),sysUser.getPassword())) {
                return ResultUtil.error(401,"密码错误");
            }  else{
                Subject currentUser  = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), sysUser.getPassword());
                currentUser.login(token);
                return ResultUtil.success();
            }

    }

    @RequestMapping(path = "/unauthorized/{message}")
    public ResultUtil unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return ResultUtil.error(401,message);
    }
}
