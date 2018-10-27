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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class LoginController {
    @Resource
    private SysUserService sysUserService;

    @MyLog(value = "用户登录")
    @RequestMapping(value = "/login",method = POST)
    public String login(@ModelAttribute(value="user")  SysUser user, Model model) {
            SysUser sysUser = sysUserService.getByLoginName(user.getLoginName());
        model.addAttribute("message","This is your message");
            if (sysUser == null) {
                return "redirect:/signin";
            } else if (!PasswordUtils.validatePassword(user.getPassword(),sysUser.getPassword())) {
                return "redirect:/signin";
            }  else{
                Subject currentUser  = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), sysUser.getPassword());
                currentUser.login(token);
                return "redirect:/";
            }



    }

    @RequestMapping(path = "/unauthorized/{message}")
    @ResponseBody
    public ResultUtil unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return ResultUtil.error(401,message);
    }
}
