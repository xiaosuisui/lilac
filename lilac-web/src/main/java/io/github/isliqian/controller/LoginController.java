package io.github.isliqian.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.service.ISysUserService;
import io.github.isliqian.shiro.jwt.JWTUtil;
import io.github.isliqian.utils.ResultUtil;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@RestController
public class LoginController {
    @Resource
    private ISysUserService userService;

    @MyLog(value = "用户登录")
    @PostMapping("/api/v1/login")
    public ResultUtil login(@RequestParam("username") String username,
                            @RequestParam("password") String password) {

        String realPassword = userService.getPassword(username);
        if (realPassword == null) {
            return ResultUtil.error(401,"用户名错误");
        } else if (!realPassword.equals(password)) {
            return ResultUtil.error(401,"密码错误");
        } else {
            return ResultUtil.success(JWTUtil.createToken(username));
        }
    }

    @RequestMapping(path = "/unauthorized/{message}")
    public ResultUtil unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return ResultUtil.error(401,message);
    }
}
