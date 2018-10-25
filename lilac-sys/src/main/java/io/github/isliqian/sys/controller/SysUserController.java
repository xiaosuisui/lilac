package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.base.Page;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.sys.service.SysUserService;
import io.github.isliqian.utils.ResultUtil;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
@Controller
@RequestMapping("/sys/user")
@Api(value = "系统用户管理")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;




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
    @GetMapping("/info")
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
}
