package io.github.isliqian.log.controller;


import io.github.isliqian.log.bean.SysLog;
import io.github.isliqian.log.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/sys/log")
@Api(value = "系统日志")
public class SysLogController {

    @Resource
    private SysLogService sysLogService;

    @GetMapping("/")
    @ApiOperation(value="日志列表")
    public ModelAndView list(SysLog sysLog, HttpServletRequest request, HttpServletResponse response, ModelAndView mav ){
        List<SysLog> logList = sysLogService.findList( sysLog);
        mav.setViewName("/sys/log.html");
        mav.addObject("list",logList);
        return mav;
    }

}
