package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.core.BaseController;
import io.github.isliqian.core.Page;
import io.github.isliqian.sys.bean.SysArea;
import io.github.isliqian.sys.service.SysAreaService;
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
@RequestMapping("/sys/area")
@Api(value = "区域配置管理接口")
public class SysAreaController extends BaseController {
    @Resource
    private SysAreaService sysAreaService;

    @ModelAttribute
    public SysArea get(@RequestParam(required=false) String id) {
        SysArea entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = sysAreaService.get(id);
        }
        if (entity == null){
            entity = new SysArea();
        }
        return entity;
    }
    @MyLog("获取全部区域")
    @GetMapping("/")
    @ApiOperation(value="获取全部区域")
    public String list(SysArea sysArea, HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("area",sysArea);
        Page<SysArea> page = sysAreaService.findPage(new Page<SysArea>(request, response), sysArea);

        model.addAttribute("page",page);
        return "/sys/area.html";
    }

    @MyLog("根据id查询区域详情")
    @GetMapping("/form")
    @ApiOperation(value="根据id查询区域详情")
    public String form(SysArea sysArea,Model model){
        model.addAttribute("area",sysArea);
        return "/sys/areaform.html";
    }




    @MyLog("保存区域")
    @PostMapping(value = "save")//@Valid
    @ApiOperation(value="保存区域")
    public String save(SysArea area, Model model) {
        if (!beanValidator(model, area)){
            return form(area, model);
        }
        sysAreaService.save(area);
        //同一页面跳转用model
        addMessage(model, "保存区域'" + area.getName() + "'成功");
        if (StringUtils.isNotBlank(area.getId())){
            return form(area,model);
        }
        return form(new SysArea(),model);
    }



    @MyLog("根据id删除区域")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除区域")
    public String delete(SysArea sysArea, RedirectAttributes redirectAttributes){
        addMessage(redirectAttributes, "删除字典成功");
        sysAreaService.delete(sysArea);
        return "redirect:/sys/area/";

    }

}
