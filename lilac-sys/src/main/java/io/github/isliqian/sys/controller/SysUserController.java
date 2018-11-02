package io.github.isliqian.sys.controller;

import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.utils.base.BaseController;
import io.github.isliqian.utils.base.Page;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.sys.service.SysUserService;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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


    @MyLog("获取全部用户")
    @GetMapping("/")
    @ApiOperation(value="获取全部用户")
    public ModelAndView list(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, ModelAndView mav ){
        Page<SysUser> page = sysUserService.findPage(new Page<SysUser>(request, response), sysUser);
        mav.setViewName("/sys/admin.html");
        mav.addObject("page",page);
        return mav;
    }

    @MyLog("根据id查询用户详情")
    @GetMapping("/form")
    @ApiOperation(value="根据id查询用户详情", notes="返回200值正确")
    public ModelAndView form(SysUser sysUser, ModelAndView modelAndView){

        if (StringUtils.isNotBlank(sysUser.getId())) {
            modelAndView.addObject("user",sysUserService.get(sysUser.getId()));
        }else {
            modelAndView.addObject("user",new SysUser());

        }
        modelAndView.setViewName("/sys/adminform.html");
        return modelAndView;
    }




    @MyLog("根据id删除用户")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除用户")
    public ModelAndView delete(SysUser sysUser,ModelAndView modelAndView){
        if (StringUtils.isNotBlank(sysUser.getId())) {
            sysUser.setDelFlag("1");
            sysUser.setUpdateDate(new Date());
            sysUserService.delete(sysUser);
            //TODO 还需删除角色关联的菜单

        } modelAndView.setViewName("redirect:/sys/user/?repage");
        return modelAndView;
    }

    @MyLog("我的资料")
    @GetMapping("/info")
    @ApiOperation(value="我的资料")
    public String info(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, ModelAndView mav ){
        SysUser user = (SysUser) redisService.get("user");
        logger.error("--------------------"+user.toString());
        return "/sys/admininfo.html";
    }
    @MyLog("修改密码")
    @GetMapping("/password")
    @ApiOperation(value="修改密码")
    public String password(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, ModelAndView mav ){
        return "/sys/adminpassword.html";
    }
}
