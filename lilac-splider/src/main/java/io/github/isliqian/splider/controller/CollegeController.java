package io.github.isliqian.splider.controller;

import io.github.isliqian.core.BaseController;
import io.github.isliqian.core.Page;
import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.splider.bean.College;
import io.github.isliqian.splider.service.CollegeService;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 学校基本信息管理
 */
@Controller
@RequestMapping("college")
public class CollegeController extends BaseController {

    @Resource
    private CollegeService collegeService;

    @ModelAttribute
    public College get(@RequestParam(required=false) String id) {
        College entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = collegeService.get(id);
        }
        if (entity == null){
            entity = new College();
        }
        return entity;
    }
    @GetMapping("/list")
    @ApiOperation(value="学校列表")
    public String list(College college, HttpServletRequest request, HttpServletResponse response, Model model ){
        model.addAttribute("college",college);
        Page<College> page = collegeService.findPage(new Page<College>(request, response), college);
        model.addAttribute("page",page);
        return "/college/college.html";
    }

    @MyLog("根据id查询学校详情")
    @GetMapping("/form")
    @ApiOperation(value="根据id查询学校详情")
    public String form(College college, Model model){
        model.addAttribute("college",college);
        return "/college/collegeform.html";
    }
    @MyLog("保存学校")
    @PostMapping(value = "save")//@Valid
    @ApiOperation(value="保存学校")
    public String save(College college, Model model) {
        if (!beanValidator(model, college)){
            return form(college, model);
        }
        collegeService.save(college);
        //同一页面跳转用model
        addMessage(model, "保存学校'" + college.getName() + "'成功");
        if (StringUtils.isNotBlank(college.getId())){
            return form(college,model);
        }
        return form(new College(),model);
    }



    @MyLog("根据id删除学校")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除学校")
    public String delete(College college, RedirectAttributes redirectAttributes){

        addMessage(redirectAttributes, "删除学校成功");
        collegeService.delete(college);
        return "redirect:/college/list";
    }
}
