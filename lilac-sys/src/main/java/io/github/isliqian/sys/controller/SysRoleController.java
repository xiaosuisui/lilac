package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.bean.SysDepartment;
import io.github.isliqian.sys.bean.SysOffice;
import io.github.isliqian.sys.service.SysDepartmentService;
import io.github.isliqian.sys.service.SysOfficeService;
import io.github.isliqian.utils.base.BaseController;
import io.github.isliqian.utils.base.Page;
import io.github.isliqian.sys.bean.SysRole;
import io.github.isliqian.sys.service.SysRoleService;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Api(value = "角色配置管理接口")
@RequestMapping("/sys/role")
@Controller
public class SysRoleController  extends BaseController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysOfficeService sysOfficeService;

    @Resource
    private SysDepartmentService sysDepartmentService;

    @ModelAttribute
    public SysRole get(@RequestParam(required=false) String id) {
        SysRole entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = sysRoleService.get(id);
        }
        if (entity == null){
            entity = new SysRole();
        }
        return entity;
    }

    @GetMapping("/")
    @ApiOperation(value="角色管理")
    public String list(SysRole sysRole, HttpServletRequest request, HttpServletResponse response, Model model ){
        sysRole.setUseable("0");
        model.addAttribute("role",sysRole);
        Page<SysRole> page = sysRoleService.findPage(new Page<SysRole>(request, response), sysRole);
        List<SysOffice> officeList = sysOfficeService.findList(new SysOffice());
//        List<SysDepartment> departmentList = sysDepartmentService.findList(new SysDepartment());
        model.addAttribute("page",page);
        model.addAttribute("officeList",officeList);
//        model.addAttribute("departmentList",departmentList);
        return "/sys/role.html";
    }

    @MyLog("根据id查询角色详情")
    @GetMapping("/form")
    @ApiOperation(value="根据id查询角色详情")
    public String form(SysRole sysRole, Model model){
        model.addAttribute("role",sysRole);
        return "/sys/roleform.html";
    }
    @MyLog("保存角色")
    @PostMapping(value = "save")//@Valid
    @ApiOperation(value="保存角色")
    public String save(SysRole sysRole, Model model) {
        if (!beanValidator(model, sysRole)){
            return form(sysRole, model);
        }
        sysRoleService.save(sysRole);
        //同一页面跳转用model
        addMessage(model, "保存角色'" + sysRole.getName() + "'成功");
        return form(new SysRole(),model);
    }


    @MyLog("根据id删除角色")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除角色")
    public String delete(SysRole sysRole, RedirectAttributes redirectAttributes){

        addMessage(redirectAttributes, "删除角色成功");
        sysRoleService.delete(sysRole);
        return "redirect:/sys/role/";
    }
}
