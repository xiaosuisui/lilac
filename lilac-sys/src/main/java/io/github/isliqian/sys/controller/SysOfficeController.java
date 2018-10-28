package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.base.Page;
import io.github.isliqian.sys.bean.SysOffice;
import io.github.isliqian.sys.service.SysOfficeService;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/sys/office")
@Api(value = "机构配置管理接口")
public class SysOfficeController {

    @Resource
    private SysOfficeService sysOfficeService;

    @GetMapping("/")
    @ApiOperation(value="机构列表")
    public ModelAndView list(SysOffice sysOffice, HttpServletRequest request, HttpServletResponse response, ModelAndView mav ){
        Page<SysOffice> page = sysOfficeService.findPage(new Page<SysOffice>(request, response), sysOffice);
        mav.setViewName("/sys/office.html");
        mav.addObject("page",page);
        return mav;
    }

    @MyLog("根据id查询机构详情")
    @GetMapping("/info")
    @ApiOperation(value="根据id查询机构详情")
    public ModelAndView form(SysOffice sysOffice, ModelAndView modelAndView){
        if (StringUtils.isNotBlank(sysOffice.getId())) {
            modelAndView.addObject("office",sysOfficeService.get(sysOffice.getId()));
        }else {
            modelAndView.addObject("office",new SysOffice());

        }
        modelAndView.setViewName("/sys/officeform.html");
        return modelAndView;
    }




    @MyLog("根据id删除机构")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除机构")
    public ModelAndView delete(SysOffice sysOffice,ModelAndView modelAndView){
        if (StringUtils.isNotBlank(sysOffice.getId())) {
            sysOffice.setUpdateDate(new Date());
            sysOfficeService.delete(sysOffice);
            //TODO 还需删除角色关联的菜单

        } modelAndView.setViewName("redirect:/sys/office/?repage");
        return modelAndView;
    }
}
