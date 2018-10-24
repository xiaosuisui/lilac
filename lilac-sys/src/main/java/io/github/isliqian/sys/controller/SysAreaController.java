package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.base.Page;
import io.github.isliqian.sys.bean.SysArea;
import io.github.isliqian.sys.bean.SysDict;
import io.github.isliqian.sys.service.SysAreaService;
import io.github.isliqian.utils.ResultUtil;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/area")
@Api(value = "区域配置管理接口")
public class SysAreaController {
    @Resource
    private SysAreaService sysAreaService;


    @MyLog("根据id查询区域详情")
    @GetMapping("/{id}")
    @ApiOperation(value="根据id查询区域详情", notes="返回200值正确")
    public ResultUtil get(@PathVariable("id") String id){
        if (StringUtils.isNotBlank(id)){
            return  ResultUtil.success(sysAreaService.get(id));
        }else{
            return ResultUtil.success(new SysArea());
        }
    }

    @MyLog("获取全部区域")
    @GetMapping("/")
    @ApiOperation(value="获取全部区域", notes="返回200值正确")
    public ResultUtil findAll(SysArea sysArea, HttpServletRequest request, HttpServletResponse response){
        //TODO 请求参数未生效
        Page<SysArea> page = sysAreaService.findPage(new Page<SysArea>(request, response), sysArea);
        return ResultUtil.success(page);
    }

    @MyLog("添加一个区域")
    @PostMapping("/")
    @ApiOperation(value="添加一个字典", notes="返回200值正确")
    public ResultUtil add(SysArea sysArea){
        sysAreaService.save(sysArea);
        return ResultUtil.success();
    }

    @MyLog("更新一个区域")
    @PutMapping("/")
    @ApiOperation(value="更新一个区域", notes="返回200值正确")
    public ResultUtil update( SysArea sysArea){
        sysAreaService.save(sysArea);
        return ResultUtil.success();
    }


    @MyLog("根据id删除区域")
    @DeleteMapping("/{id}")
    @ApiOperation(value="根据id删除区域", notes="返回200值正确")
    public ResultUtil delete(@PathVariable("id") String id){
        if (StringUtils.isNotBlank(id)) {
            SysArea sysArea = new SysArea();
            sysArea.setId(id);
            sysAreaService.delete(sysArea);
            return ResultUtil.success();
        }else {
            return ResultUtil.error(-1,"id不能为空");
        }

    }

}
