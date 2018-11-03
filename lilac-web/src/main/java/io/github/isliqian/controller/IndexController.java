package io.github.isliqian.controller;

import io.github.isliqian.bean.Console;
import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.sys.service.SysUserService;
import io.github.isliqian.core.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@Api(value = "路由控制管理")
public class IndexController extends BaseController {

    @Resource
    private RedisService redisService;

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/")
    @ApiOperation(value="首页")
    public String index(Model model){
      SysUser user = (SysUser) redisService.get("user");
      if (user==null){
          return "redirect:/signin";
      }else {
          addMessage(model,"欢迎使用lilac后台管理系统 V1.0.0");
          model.addAttribute("user",user);
      }
      return "index.html";
    }
    @GetMapping("/signin")
    @ApiOperation(value="登陆")
    public String signin(Model model){
        model.addAttribute("user",new SysUser());
        return "/user/login.html";
    }
    @GetMapping("/password")
    @ApiOperation(value="找回密码")
    public ModelAndView password(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/user/forget.html");
        return mav;
    }
    @GetMapping("/home/console.html")
    @ApiOperation(value="控制面板")
    public String home(Model model){

        Console console = (Console) redisService.get("console");
        if (console == null){
            console = new Console();
            console.setVisitCount(Long.valueOf(1));
            console.setUserCount(Long.valueOf(sysUserService.findAll().size()));
            redisService.set("console",console);
        }else {
            console.setVisitCount(1+console.getVisitCount());
            redisService.set("console",console);
        }
        model.addAttribute("console", console);         // 后台参数传递给前端
        return "/home/console.html";
    }
    @GetMapping("/set/system/website")
    @ApiOperation(value="网站设置")
    public ModelAndView website(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/set/system/website.html");
        return mav;
    }

    @GetMapping("/set/system/email")
    @ApiOperation(value="邮件服务")
    public ModelAndView email(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/set/system/email.html");
        return mav;
    }




}
