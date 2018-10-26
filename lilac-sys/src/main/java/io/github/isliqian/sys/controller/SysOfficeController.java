package io.github.isliqian.sys.controller;

import io.github.isliqian.sys.base.Page;
import io.github.isliqian.sys.bean.SysOffice;
import io.github.isliqian.sys.service.SysOfficeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

}
