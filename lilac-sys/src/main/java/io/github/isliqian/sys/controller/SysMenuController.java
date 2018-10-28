package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.base.Page;
import io.github.isliqian.sys.bean.SysMenu;
import io.github.isliqian.sys.service.SysMenuService;
import io.github.isliqian.utils.ResultUtil;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/sys/menu")
@Api(value = "菜单配置管理接口")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;


    @MyLog("根据id查询菜单详情")
    @GetMapping("/info")
    @ApiOperation(value="根据id查询菜单详情")
    public String form(SysMenu sysMenu,Model model){
        if (StringUtils.isNotBlank(sysMenu.getId())) {
            model.addAttribute("menu",sysMenuService.get(sysMenu.getId()));
        }else {
            model.addAttribute("menu",new SysMenu());

        }
        return "/sys/menuform.html";
    }

    @MyLog("获取全部菜单")
    @GetMapping("/")
    @ApiOperation(value="获取全部菜单", notes="返回200值正确")
    public String findAll(SysMenu sysMenu, HttpServletRequest request, HttpServletResponse response, Model model){
        //TODO 请求参数未生效
        Page<SysMenu> page = sysMenuService.findPage(new Page<SysMenu>(request, response), sysMenu);
        model.addAttribute("page",page);
        return "/sys/menu.html";
    }




    @MyLog("根据id删除菜单")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除菜单", notes="返回200值正确")
    public String delete(SysMenu sysMenu){
        if (StringUtils.isNotBlank(sysMenu.getId())) {
            sysMenu.setUpdateDate(new Date());
            sysMenuService.delete(sysMenu);
        }
        return "redirect:/sys/area/?repage";

    }

}
