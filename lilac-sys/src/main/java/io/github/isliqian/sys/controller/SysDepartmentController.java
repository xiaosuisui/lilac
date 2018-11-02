package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.bean.SysDepartment;
import io.github.isliqian.sys.bean.SysDict;
import io.github.isliqian.sys.bean.SysOffice;
import io.github.isliqian.sys.bean.SysRole;
import io.github.isliqian.sys.service.SysDepartmentService;
import io.github.isliqian.sys.service.SysOfficeService;
import io.github.isliqian.sys.service.SysRoleService;
import io.github.isliqian.utils.StringUtils;
import io.github.isliqian.utils.base.BaseController;
import io.github.isliqian.utils.base.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "部门配置管理接口")
@RequestMapping("/sys/department")
@Controller
public class SysDepartmentController extends BaseController {

    @Resource
    private SysDepartmentService sysDepartmentService;

    @Resource
    private SysOfficeService sysOfficeService;

    @ModelAttribute
    public SysDepartment get(@RequestParam(required=false) String id) {
        SysDepartment entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = sysDepartmentService.get(id);
        }
        if (entity == null){
            entity = new SysDepartment();
        }
        return entity;
    }
    @GetMapping("/")
    @ApiOperation(value="部门管理")
    public String list(SysDepartment sysDepartment, HttpServletRequest request, HttpServletResponse response, Model model ){
        model.addAttribute("department",sysDepartment);
        Page<SysDepartment> page = sysDepartmentService.findPage(new Page<SysDepartment>(request, response), sysDepartment);
        model.addAttribute("page",page);
        return "/sys/department.html";
    }

    @MyLog("根据id查询部门详情")
    @GetMapping("/form")
    @ApiOperation(value="根据id查询部门详情")
    public String form(SysDepartment sysDepartment, Model model){
        model.addAttribute("department",sysDepartment);
        List<SysOffice> officeList  = sysOfficeService.findList(new SysOffice());
        model.addAttribute("officeList",officeList);
        return "/sys/departmentform.html";
    }


    @MyLog("保存部门")
    @PostMapping(value = "save")//@Valid
    @ApiOperation(value="保存部门")
    public String save(SysDepartment department, Model model) {
        if (!beanValidator(model, department)){
            return form(department, model);
        }
        sysDepartmentService.save(department);
        //同一页面跳转用model
        addMessage(model, "保存机构'" + department.getName() + "'成功");
        if (StringUtils.isNotBlank(department.getId())){
            return form(department,model);
        }
        return form(new SysDepartment(),model);
    }

    @MyLog("根据id删除部门")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除部门")
    public String delete(SysDepartment sysDepartment, RedirectAttributes redirectAttributes){
        addMessage(redirectAttributes, "删除部门成功");
        sysDepartmentService.delete(sysDepartment);
        return "redirect:/sys/department/";
    }
}
