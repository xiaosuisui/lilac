package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.utils.base.Page;
import io.github.isliqian.sys.bean.SysRole;
import io.github.isliqian.sys.service.SysRoleService;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Api(value = "角色配置管理接口")
@RequestMapping("/sys/role")
@Controller
public class SysRoleController  {

    @Resource
    private SysRoleService sysRoleService;

    @GetMapping("/")
    @ApiOperation(value="角色管理")
    public ModelAndView list(SysRole sysRole, HttpServletRequest request, HttpServletResponse response, ModelAndView mav ){
        sysRole.setUseable("0");
        Page<SysRole> page = sysRoleService.findPage(new Page<SysRole>(request, response), sysRole);
        mav.setViewName("/sys/role.html");
        mav.addObject("page",page);
        return mav;
    }

    @MyLog("根据id查询角色详情")
    @GetMapping("/info")
    @ApiOperation(value="根据id查询角色详情")
    public ModelAndView form(SysRole sysRole, ModelAndView modelAndView){
        if (StringUtils.isNotBlank(sysRole.getId())) {
            modelAndView.addObject("role",sysRoleService.get(sysRole.getId()));
        }else {
            modelAndView.addObject("role",new SysRole());

        }
        modelAndView.setViewName("/sys/roleform.html");
        return modelAndView;
    }




    @MyLog("根据id删除角色")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除角色")
    public ModelAndView delete(SysRole sysRole,ModelAndView modelAndView){
        if (StringUtils.isNotBlank(sysRole.getId())) {
            sysRole.setDelFlag("1");
            sysRole.setUpdateDate(new Date());
            sysRoleService.delete(sysRole);
            //TODO 还需删除角色关联的菜单

        } modelAndView.setViewName("redirect:/sys/role/?repage");
        return modelAndView;
    }
}
