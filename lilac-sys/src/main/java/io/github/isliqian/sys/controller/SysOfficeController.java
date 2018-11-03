package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.bean.SysArea;
import io.github.isliqian.sys.service.SysAreaService;
import io.github.isliqian.core.BaseController;
import io.github.isliqian.core.Page;
import io.github.isliqian.sys.bean.SysOffice;
import io.github.isliqian.sys.service.SysOfficeService;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sys/office")
@Api(value = "机构配置管理接口")
public class SysOfficeController extends BaseController {

    @Resource
    private SysOfficeService sysOfficeService;

    @Resource
    private SysAreaService sysAreaService;

    @ModelAttribute
    public SysOffice get(@RequestParam(required=false) String id) {
        SysOffice entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = sysOfficeService.get(id);
        }
        if (entity == null){
            entity = new SysOffice();
        }
        return entity;
    }
    @GetMapping("/")
    @ApiOperation(value="机构列表")
    public String list(SysOffice sysOffice, HttpServletRequest request, HttpServletResponse response,  Model model){
        Page<SysOffice> page = sysOfficeService.findPage(new Page<SysOffice>(request, response), sysOffice);
        model.addAttribute("page",page);
        return "/sys/office.html";
    }

    @MyLog("根据id查询机构详情")
    @GetMapping("/form")
    @ApiOperation(value="根据id查询机构详情")
    public String form(SysOffice sysOffice, Model model){
        List<SysArea> areaList = sysAreaService.findList(new SysArea());
        model.addAttribute("areaList",areaList);
        model.addAttribute("office",sysOffice);
        return "/sys/officeform.html";
    }


    @MyLog("保存机构")
    @PostMapping(value = "save")//@Valid
    @ApiOperation(value="保存机构")
    public String save(SysOffice office, Model model) {
        if (!beanValidator(model, office)){
            return form(office, model);
        }
        sysOfficeService.save(office);
        //同一页面跳转用model
        addMessage(model, "保存机构'" + office.getName() + "'成功");
        if (StringUtils.isNotBlank(office.getId())){
            return form(office,model);
        }
        return form(new SysOffice(),model);
    }

    @MyLog("根据id删除机构")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除机构")
    public String delete(SysOffice sysOffice, RedirectAttributes redirectAttributes){
        addMessage(redirectAttributes, "删除机构成功");
        sysOfficeService.delete(sysOffice);
        return "redirect:/sys/office/";
    }
}
