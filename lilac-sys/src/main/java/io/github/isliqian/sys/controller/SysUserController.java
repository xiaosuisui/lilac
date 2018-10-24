package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.base.Page;
import io.github.isliqian.sys.bean.SysUser;
import io.github.isliqian.sys.service.SysUserService;
import io.github.isliqian.utils.ResultUtil;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@Api(value = "系统用户管理接口")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @MyLog("根据id查询用户详情")
    @GetMapping("/{id}")
    @ApiOperation(value="根据id查询角色详情", notes="返回200值正确")
    public ResultUtil get(@PathVariable("id") String id){
        if (StringUtils.isNotBlank(id)){
            return  ResultUtil.success(sysUserService.get(id));
        }else{
            return ResultUtil.success(new SysUser());
        }
    }

    @MyLog("获取全部用户")
    @GetMapping("/")
    @ApiOperation(value="获取全部角色", notes="返回200值正确")
    public ResultUtil findAll(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
        //TODO 请求参数未生效
        Page<SysUser> page = sysUserService.findPage(new Page<SysUser>(request, response), sysUser);
        return ResultUtil.success(page);
    }

    @MyLog("添加一个用户")
    @PostMapping("/")
    @ApiOperation(value="添加一个角色", notes="返回200值正确")
    public ResultUtil add(@Valid SysUser sysUser){
        //TODO 需要保存角色关联菜单
        sysUserService.save(sysUser);
        return ResultUtil.success();
    }

    @MyLog("更新一个区域")
    @PutMapping("/")
    @ApiOperation(value="更新一个角色", notes="返回200值正确")
    public ResultUtil update( SysUser sysUser){
        sysUserService.save(sysUser);
        return ResultUtil.success();
    }


    @MyLog("根据id删除用户")
    @DeleteMapping("/{id}")
    @ApiOperation(value="根据id删除用户", notes="返回200值正确")
    public ResultUtil delete(@PathVariable("id") String id){
        if (StringUtils.isNotBlank(id)) {
            SysUser sysUser = new SysUser();
            sysUser.setId(id);
            sysUserService.delete(sysUser);
            //TODO 还需删除角色关联的菜单
            return ResultUtil.success();
        }else {
            return ResultUtil.error(-1,"id不能为空");
        }

    }
}
