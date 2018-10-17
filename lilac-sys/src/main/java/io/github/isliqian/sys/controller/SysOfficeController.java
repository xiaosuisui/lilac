package io.github.isliqian.sys.controller;

import io.github.isliqian.sys.service.SysOfficeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/office")
@Api(value = "机构配置管理接口")
public class SysOfficeController {

    @Resource
    private SysOfficeService sysOfficeService;



}
