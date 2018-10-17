package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.base.Page;
import io.github.isliqian.sys.bean.SysArea;
import io.github.isliqian.sys.bean.SysRole;
import io.github.isliqian.sys.service.SysRoleService;
import io.github.isliqian.utils.ResultUtil;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/role")
@Api(value = "角色配置管理接口")
public class SysRoleController  {

    @Resource
    private SysRoleService sysRoleService;

    @MyLog("根据id查询角色详情")
    @GetMapping("/{id}")
    @ApiOperation(value="根据id查询角色详情", notes="返回200值正确")
    public ResultUtil get(@PathVariable("id") String id){
        if (StringUtils.isNotBlank(id)){
            return  ResultUtil.success(sysRoleService.get(id));
        }else{
            return ResultUtil.success(new SysRole());
        }
    }

    @MyLog("获取全部角色")
    @GetMapping("/")
    @ApiOperation(value="获取全部角色", notes="返回200值正确")
    public ResultUtil findAll(SysRole sysRole, HttpServletRequest request, HttpServletResponse response){
        //TODO 请求参数未生效
        Page<SysRole> page = sysRoleService.findPage(new Page<SysRole>(request, response), sysRole);
        return ResultUtil.success(page);
    }

    @MyLog("添加一个角色")
    @PostMapping("/")
    @ApiOperation(value="添加一个角色", notes="返回200值正确")
    public ResultUtil add(@ModelAttribute("sysRole") SysRole sysRole){
        //TODO 需要保存角色关联菜单
        sysRoleService.save(sysRole);
        return ResultUtil.success();
    }

    @MyLog("更新一个区域")
    @PutMapping("/")
    @ApiOperation(value="更新一个角色", notes="返回200值正确")
    public ResultUtil update(@ModelAttribute("sysRole") SysRole sysRole){
        sysRoleService.save(sysRole);
        return ResultUtil.success();
    }


    @MyLog("根据id删除角色")
    @DeleteMapping("/{id}")
    @ApiOperation(value="根据id删除角色", notes="返回200值正确")
    public ResultUtil delete(@PathVariable("id") String id){
        if (StringUtils.isNotBlank(id)) {
            SysRole sysRole = new SysRole();
            sysRole.setId(id);
            sysRoleService.delete(sysRole);
            //TODO 还需删除角色关联的菜单
            return ResultUtil.success();
        }else {
            return ResultUtil.error(-1,"id不能为空");
        }

    }
}
