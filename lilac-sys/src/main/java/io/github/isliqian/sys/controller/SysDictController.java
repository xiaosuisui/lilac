package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.base.Page;
import io.github.isliqian.sys.bean.SysDict;
import io.github.isliqian.sys.service.SysDictService;
import io.github.isliqian.utils.ResultUtil;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dict")
@Api(value = "字典配置管理接口")
public class SysDictController  {

    @Resource
    private SysDictService sysDictService;

    @MyLog("根据id查询字典详情")
    @GetMapping("/{id}")
    @ApiOperation(value="根据id查询字典详情", notes="返回200值正确")
    public ResultUtil get(@PathVariable("id") String id){
        if (StringUtils.isNotBlank(id)){
            return  ResultUtil.success(sysDictService.get(id));
        }else{
            return ResultUtil.success(new SysDict());
        }
    }

    @MyLog("获取全部字典")
    @GetMapping("/")
    @ApiOperation(value="获取全部字典", notes="返回200值正确")
    public ResultUtil findAll(SysDict sysDict,HttpServletRequest request, HttpServletResponse response){
        //TODO 请求参数未生效
        Page<SysDict> page = sysDictService.findPage(new Page<SysDict>(request, response), sysDict);
        return ResultUtil.success(page);
    }

    @MyLog("添加一个字典")
    @PostMapping("/")
    @ApiOperation(value="添加一个字典", notes="返回200值正确")
    public ResultUtil add(@ModelAttribute("sysDict") SysDict sysDict){
        sysDictService.save(sysDict);
        return ResultUtil.success();
    }

    @MyLog("更新一个字典")
    @PutMapping("/")
    @ApiOperation(value="更新一个字典", notes="返回200值正确")
    public ResultUtil update(@ModelAttribute("sysDict") SysDict sysDict){
        sysDictService.save(sysDict);
        return ResultUtil.success();
    }

    @MyLog("获取全部字典类型")
    @GetMapping("/type")
    @ApiOperation(value="获取全部字典类型", notes="返回200值正确")
    public ResultUtil findTypeList(){
        List<String> typeList = sysDictService.findTypeList();
        return ResultUtil.success(typeList);

    }

    @MyLog("根据id删除字典")
    @DeleteMapping("/{id}")
    @ApiOperation(value="根据id删除字典", notes="返回200值正确")
    public ResultUtil delete(@PathVariable("id") String id){
        if (StringUtils.isNotBlank(id)) {
            SysDict sysDict = new SysDict();
            sysDict.setId(id);
            sysDictService.delete(sysDict);
            return ResultUtil.success();
        }else {
            return ResultUtil.error(-1,"id不能为空");
        }

    }




}
