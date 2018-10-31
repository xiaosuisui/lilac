package io.github.isliqian.controller;

import io.github.isliqian.sys.service.SysRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wxt.liqian
 * @version 2018/10/26
 * @desc 微信用户
 */
@Controller
@RequestMapping("/wx/user")
public class WxUserController {

    @Resource
    private SysRoleService sysRoleService;

    @GetMapping("/")
    @ApiOperation(value="网站用户管理")
    public ModelAndView list( HttpServletRequest request, HttpServletResponse response, ModelAndView mav ){

        mav.setViewName("/wx/user.html");
        return mav;
    }

//    @MyLog("根据id查询角色详情")
//    @GetMapping("/info")
//    @ApiOperation(value="根据id查询角色详情")
//    public ModelAndView form(SysRole sysRole, ModelAndView modelAndView){
//        if (StringUtils.isNotBlank(sysRole.getId())) {
//            modelAndView.addObject("role",sysRoleService.get(sysRole.getId()));
//        }else {
//            modelAndView.addObject("role",new SysRole());
//
//        }
//        modelAndView.setViewName("/sys/roleform.html");
//        return modelAndView;
//    }
//
//
//
//
//    @MyLog("根据id删除角色")
//    @GetMapping("/del")
//    @ApiOperation(value="根据id删除角色")
//    public ModelAndView delete(SysRole sysRole,ModelAndView modelAndView){
//        if (StringUtils.isNotBlank(sysRole.getId())) {
//            sysRole.setDelFlag("1");
//            sysRole.setUpdateDate(new Date());
//            sysRoleService.delete(sysRole);
//            //TODO 还需删除角色关联的菜单
//
//        } modelAndView.setViewName("redirect:/sys/role/?repage");
//        return modelAndView;
//    }
}
