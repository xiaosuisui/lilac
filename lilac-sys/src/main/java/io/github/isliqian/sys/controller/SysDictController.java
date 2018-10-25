package io.github.isliqian.sys.controller;

import io.github.isliqian.log.ann.MyLog;
import io.github.isliqian.sys.base.Page;
import io.github.isliqian.sys.bean.SysDict;
import io.github.isliqian.sys.service.SysDictService;
import io.github.isliqian.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/sys/dict")
@Api(value = "字典管理")
public class SysDictController  {

    @Resource
    private SysDictService sysDictService;
    @GetMapping("/")
    @ApiOperation(value="字典列表")
    public ModelAndView list(SysDict sysDict, HttpServletRequest request, HttpServletResponse response, ModelAndView mav ){
        Page<SysDict> page = sysDictService.findPage(new Page<SysDict>(request, response), sysDict);
        mav.setViewName("/sys/dict.html");
        mav.addObject("page",page);
        return mav;
    }

    @MyLog("根据id查询字典详情")
    @GetMapping("/info")
    @ApiOperation(value="根据id查询字典详情")
    public ModelAndView form(SysDict sysDict, ModelAndView modelAndView){
        if (StringUtils.isNotBlank(sysDict.getId())) {
            modelAndView.addObject("dict",sysDictService.get(sysDict.getId()));
        }else {
            modelAndView.addObject("dict",new SysDict());

        }
        modelAndView.setViewName("/sys/dictform.html");
        return modelAndView;
    }




    @MyLog("根据id删除字典")
    @GetMapping("/del")
    @ApiOperation(value="根据id删除字典")
    public ModelAndView delete(SysDict sysDict, ModelAndView modelAndView){
        if (StringUtils.isNotBlank(sysDict.getId())) {
            sysDict.setDelFlag("1");
            sysDict.setUpdateDate(new Date());
            sysDictService.delete(sysDict);
        } modelAndView.setViewName("redirect:/sys/dict/?repage");
        return modelAndView;
    }





}
