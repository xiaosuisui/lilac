package io.github.isliqian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Api(value = "路由控制管理")
public class IndexController {


    @GetMapping("/")
    @ApiOperation(value="首页")
    public ModelAndView index(){
      ModelAndView mav = new ModelAndView();
      mav.setViewName("index");
      return mav;
    }

    @GetMapping("/home/console.html")
    @ApiOperation(value="控制面板")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/home/console.html");
        return mav;
    }


}
