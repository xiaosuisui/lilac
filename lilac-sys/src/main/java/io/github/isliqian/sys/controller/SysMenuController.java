package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.core.BaseController;
import io.github.isliqian.core.Page;
import io.github.isliqian.sys.bean.SysMenu;
import io.github.isliqian.sys.service.SysMenuService;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sys/menu")
@Api(value = "菜单配置管理接口")
public class SysMenuController extends BaseController {

    @Resource
    private SysMenuService sysMenuService;

    @ModelAttribute
    public SysMenu get(@RequestParam(required=false) String id) {
        SysMenu entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = sysMenuService.get(id);
        }
        if (entity == null){
            entity = new SysMenu();
        }
        return entity;
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

    @MyLog("根据id查询菜单详情")
    @GetMapping("/form")
    @ApiOperation(value="根据id查询菜单详情")
    public String form(SysMenu sysMenu,Model model){
        model.addAttribute("menu",sysMenu);
        return "/sys/menuform.html";
    }


    @MyLog("保存菜单")
    @PostMapping(value = "save")//@Valid
    @ApiOperation(value="保存菜单")
    public String save(SysMenu menu, Model model) {
        if (!beanValidator(model, menu)){
            return form(menu, model);
        }
        sysMenuService.save(menu);
        //同一页面跳转用model
        addMessage(model, "保存菜单'" + menu.getName() + "'成功");
        if (StringUtils.isNotBlank(menu.getId())){
            return form(menu,model);
        }
        return form(new SysMenu(),model);
    }



    @MyLog("根据id删除菜单")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除菜单")
    public String delete(SysMenu sysMenu, RedirectAttributes redirectAttributes){
        addMessage(redirectAttributes, "删除部门成功");
            sysMenuService.delete(sysMenu);
        return "redirect:/sys/menu/";
    }

}
