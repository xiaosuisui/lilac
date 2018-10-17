package io.github.isliqian.sys.controller;

import io.github.isliqian.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/dict")
@Api(value = "菜单配置管理接口")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;


}
