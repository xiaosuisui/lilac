package io.github.isliqian.sys.controller;

import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.bean.SysDict;
import io.github.isliqian.utils.PasswordUtils;
import io.github.isliqian.utils.base.BaseController;
import io.github.isliqian.utils.base.Page;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.sys.service.SysUserService;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
@Controller
@RequestMapping("/sys/user")
@Api(value = "系统用户管理")
public class SysUserController extends BaseController {


    @Resource
    private SysUserService sysUserService;

    @Resource
    private RedisService redisService;
    @ModelAttribute
    public SysUser get(@RequestParam(required=false) String id) {
        SysUser entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = sysUserService.get(id);
        }
        if (entity == null){
            entity = new SysUser();
        }
        return entity;
    }

    @MyLog("获取全部用户")
    @GetMapping("/")
    @ApiOperation(value="获取全部用户")
    public String list(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, Model model ){
        model.addAttribute("user",sysUser);
        Page<SysUser> page = sysUserService.findPage(new Page<SysUser>(request, response), sysUser);
        model.addAttribute("page",page);
        return "/sys/admin.html";
    }

    @MyLog("根据id查询用户详情")
    @GetMapping("/form")
    @ApiOperation(value="根据id查询用户详情")
    public String form(SysUser sysUser, Model model){

        model.addAttribute("user",sysUser);
        return "/sys/adminform.html";
    }
    @MyLog("保存用户")
    @PostMapping(value = "save")//@Valid
    @ApiOperation(value="保存用户")
    public String save(SysUser user, Model model) {
        if (!beanValidator(model, user)){
            return form(user, model);
        }
        sysUserService.save(user);
        //同一页面跳转用model
        addMessage(model, "保存'" + user.getLoginName() + "'信息成功");
        return form(new SysUser(),model);
    }


    @MyLog("根据id删除用户")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除用户")
    public String delete(SysUser sysUser, RedirectAttributes redirectAttributes){

        addMessage(redirectAttributes, "删除用户成功");
        sysUserService.delete(sysUser);
        return "redirect:/sys/admin/";
    }

    @MyLog("我的资料")
    @GetMapping("/info")
    @ApiOperation(value="我的资料")
    public String info(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, Model model ){
        SysUser user = (SysUser) redisService.get("user");
        model.addAttribute("user",user);
        return "/sys/admininfo.html";
    }
    @MyLog("修改密码")
    @GetMapping("/password")
    @ApiOperation(value="修改密码")
    public String password(  Model model){
        SysUser user = (SysUser) redisService.get("user");
        model.addAttribute("user",user);
        return "/sys/adminpassword.html";
    }
}
