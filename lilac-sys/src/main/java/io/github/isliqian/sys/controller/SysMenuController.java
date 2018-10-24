package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.base.Page;
import io.github.isliqian.sys.bean.SysMenu;
import io.github.isliqian.sys.service.SysMenuService;
import io.github.isliqian.utils.ResultUtil;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/menu")
@Api(value = "菜单配置管理接口")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;


    @MyLog("根据id查询菜单详情")
    @GetMapping("/{id}")
    @ApiOperation(value="根据id查询菜单详情", notes="返回200值正确")
    public ResultUtil get(@PathVariable("id") String id){
        if (StringUtils.isNotBlank(id)){
            return  ResultUtil.success(sysMenuService.get(id));
        }else{
            return ResultUtil.success(new SysMenu());
        }
    }

    @MyLog("获取全部菜单")
    @GetMapping("/")
    @ApiOperation(value="获取全部菜单", notes="返回200值正确")
    public ResultUtil findAll(SysMenu sysMenu, HttpServletRequest request, HttpServletResponse response){
        //TODO 请求参数未生效
        Page<SysMenu> page = sysMenuService.findPage(new Page<SysMenu>(request, response), sysMenu);
        return ResultUtil.success(page);
    }

    @MyLog("添加一个菜单")
    @PostMapping("/")
    @ApiOperation(value="添加一个菜单", notes="返回200值正确")
    public ResultUtil add( SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return ResultUtil.success();
    }

    @MyLog("更新一个菜单")
    @PutMapping("/")
    @ApiOperation(value="更新一个菜单", notes="返回200值正确")
    public ResultUtil update( SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return ResultUtil.success();
    }



    @MyLog("根据id删除菜单")
    @DeleteMapping("/{id}")
    @ApiOperation(value="根据id删除菜单", notes="返回200值正确")
    public ResultUtil delete(@PathVariable("id") String id){
        if (StringUtils.isNotBlank(id)) {
            SysMenu sysMenu = new SysMenu();
            sysMenu.setId(id);
            sysMenuService.delete(sysMenu);
            return ResultUtil.success();
        }else {
            return ResultUtil.error(-1,"id不能为空");
        }

    }

}
