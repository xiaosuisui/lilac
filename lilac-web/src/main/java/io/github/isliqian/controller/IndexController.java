package io.github.isliqian.controller;

import io.github.isliqian.sys.bean.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Api(value = "路由控制管理")
public class IndexController {


    @GetMapping("/")
    @ApiOperation(value="首页")
    public ModelAndView index(){
      ModelAndView mav = new ModelAndView();
      mav.setViewName("index.html");
      return mav;
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
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/home/console.html");
        return mav;
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

    @GetMapping("/mybatis/generator")
    @ApiOperation(value="代码生成")
    public ModelAndView generator(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/plug/generator.html");
        return mav;
    }
}
