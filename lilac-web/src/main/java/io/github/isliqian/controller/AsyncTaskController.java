package io.github.isliqian.controller;

import io.github.isliqian.async.bean.TaskInfo;
import io.github.isliqian.async.manager.AsyncTaskManager;
import io.github.isliqian.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/v1/asynctask")
public class AsyncTaskController {
    //注入异步任务管理器
    @Resource
    private AsyncTaskManager asyncTaskManager;
    /**
     * 开启异步线程
     * @return
     */
    @GetMapping(value = "/start")
    public ResultUtil startAsyncTask() {
        //调用任务管理器中的submit去提交一个异步任务
        TaskInfo taskInfo = asyncTaskManager.submit(() -> {
            System.out.println("__________");
            try {
                //模拟异步，睡眠30秒
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("__________");
        });
        return ResultUtil.success(taskInfo);
    }

    @GetMapping(value = "/status/{taskId}")
    public ResultUtil getTaskStatus(
            @PathVariable("taskId") String taskId) {
        return ResultUtil.success(asyncTaskManager.getTaskInfo(taskId));
    }
}

