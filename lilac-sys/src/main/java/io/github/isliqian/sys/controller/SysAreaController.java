package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.mapper.SysAreaMapper;
import io.github.isliqian.sys.utils.MicAppContext;
import io.github.isliqian.utils.base.BaseController;
import io.github.isliqian.utils.base.Page;
import io.github.isliqian.sys.bean.SysArea;
import io.github.isliqian.sys.service.SysAreaService;
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
        SysAreaService sysAreaService1 = MicAppContext.getBean("sysAreaService");
        Page<SysArea> page = sysAreaService1.findPage(new Page<SysArea>(request, response), sysArea);

        model.addAttribute("page",page);
        return "/sys/area.html";
    }

    @MyLog("根据id查询区域详情")
    @GetMapping("/info")
    @ApiOperation(value="根据id查询区域详情")
    public String form(SysArea sysArea,Model model){
        if (StringUtils.isNotBlank(sysArea.getId())) {
            model.addAttribute("area",sysAreaService.get(sysArea.getId()));
        }else {
            model.addAttribute("area",new SysArea());

        }
       return "/sys/areaform.html";
    }








    @MyLog("根据id删除区域")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除区域")
    public String delete(SysArea sysArea,Model model){
        if (StringUtils.isNotBlank(sysArea.getId())) {
            sysArea.setUpdateDate(new Date());
            sysAreaService.delete(sysArea);
        }
        return "redirect:/sys/area/?repage";

    }

}
