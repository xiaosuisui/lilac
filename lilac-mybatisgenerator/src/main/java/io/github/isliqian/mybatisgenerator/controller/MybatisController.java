package io.github.isliqian.mybatisgenerator.controller;

import com.alibaba.fastjson.JSONObject;
import io.github.isliqian.mybatisgenerator.service.GenService;
import io.github.isliqian.utils.base.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lis on 17/5/11.
 */
@Controller
public class MybatisController extends BaseController {

    @Autowired
    public GenService genService;

    @GetMapping("/plug/mybatis/")
    @ApiOperation(value="代码生成")
    public String generator(Model model){
        addMessage(model,"欢迎使用lilac代码生成器 V1.0");
        return "/plug/generator.html";
    }

    @RequestMapping("/gen")
    @ResponseBody
    public String generator(HttpServletRequest request) {

        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", genService.genCode(request));
        jsonObject.put("basepath", basePath);


        return jsonObject.toJSONString();
    }


}