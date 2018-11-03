package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.core.BaseController;
import io.github.isliqian.core.Page;
import io.github.isliqian.sys.bean.SysDict;
import io.github.isliqian.sys.service.SysDictService;
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
@RequestMapping("/sys/dict")
@Api(value = "字典管理")
public class SysDictController  extends BaseController {

    @Resource
    private SysDictService sysDictService;

    @ModelAttribute
    public SysDict get(@RequestParam(required=false) String id) {
        SysDict entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = sysDictService.get(id);
        }
        if (entity == null){
            entity = new SysDict();
        }
        return entity;
    }
    @GetMapping("/")
    @ApiOperation(value="字典列表")
    public String list(SysDict sysDict, HttpServletRequest request, HttpServletResponse response, Model model ){
        model.addAttribute("dict",sysDict);
        Page<SysDict> page = sysDictService.findPage(new Page<SysDict>(request, response), sysDict);
        model.addAttribute("page",page);
        return "/sys/dict.html";
    }

    @MyLog("根据id查询字典详情")
    @GetMapping("/form")
    @ApiOperation(value="根据id查询字典详情")
    public String form(SysDict sysDict, Model model){
        model.addAttribute("dict",sysDict);
        return "/sys/dictform.html";
    }
    @MyLog("保存字典")
    @PostMapping(value = "save")//@Valid
    @ApiOperation(value="保存字典")
    public String save(SysDict dict, Model model) {
        if (!beanValidator(model, dict)){
            return form(dict, model);
        }
        sysDictService.save(dict);
        //同一页面跳转用model
        addMessage(model, "保存字典'" + dict.getLabel() + "'成功");
        if (StringUtils.isNotBlank(dict.getId())){
            return form(dict,model);
        }
        return form(new SysDict(),model);
    }



    @MyLog("根据id删除字典")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除字典")
    public String delete(SysDict sysDict, RedirectAttributes redirectAttributes){

        addMessage(redirectAttributes, "删除字典成功");
        sysDictService.delete(sysDict);
        return "redirect:/sys/dict/";
    }





}
