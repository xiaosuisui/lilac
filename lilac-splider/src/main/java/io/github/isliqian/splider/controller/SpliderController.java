package io.github.isliqian.splider.controller;

import io.github.isliqian.async.bean.TaskInfo;
import io.github.isliqian.async.manager.AsyncTaskManager;
import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.core.BaseController;
import io.github.isliqian.splider.service.BasicInfoService;
import io.github.isliqian.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author wxt.liqian
 * @version 2018/11/3
 * @desc
 */
@Controller
@RequestMapping("/splider")
public class SpliderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SpliderController.class);

    @Resource
    private BasicInfoService basicInfoService;

    @Resource
    private AsyncTaskManager asyncTaskManager;

    @Resource
    private RedisService redisService;

    @GetMapping("/index")
    @ApiOperation(value="跳转到爬取数据页面")
    public String index(Model model){
        addMessage(model,"爬虫模块,内测版本");
        return "/splider/index.html";
    }

    @GetMapping("/college")
    @ResponseBody
    public ResultUtil collge(){
        TaskInfo info = (TaskInfo) redisService.get("collegeInfo");
        if (info == null){
            //异步爬取信息
            TaskInfo taskInfo = asyncTaskManager.submit(() -> {
                basicInfoService.start();
            });
            redisService.set("collegeInfo",taskInfo);
            return ResultUtil.success(taskInfo);
        }else {
            return ResultUtil.error(-1,"正在执行爬取高校基本信息操作,请勿重复执行");
        }

    }
    @GetMapping("/historicalLine")
    @ResponseBody
    public ResultUtil historicalLine(){
        TaskInfo info = (TaskInfo) redisService.get("historicalLine");
        if (info == null){
            //异步爬取信息
            TaskInfo taskInfo = asyncTaskManager.submit(() -> {
//                historicalLine.start();
            });
            redisService.set("historicalLine",taskInfo);
            return ResultUtil.success(taskInfo);
        }else {
            return ResultUtil.error(-1,"正在执行爬取高校基本信息操作,请勿重复执行");
        }

    }
}
