package io.github.isliqian.async.manager;



import io.github.isliqian.async.eum.TaskStatusEnum;
import io.github.isliqian.async.bean.TaskInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by shirukai on 2018/7/31
 * 异步任务监控
 */
@Component
@Aspect
public class AsyncTaskMonitor {
    @Autowired
    AsyncTaskManager manager;
    private static Logger LOG = LoggerFactory.getLogger(AsyncTaskMonitor.class);

    @Around("execution(* io.github.isliqian.async.service.AsyncTaskExecutor.*(..))")
    public void taskHandle(ProceedingJoinPoint pjp) {
        //获取taskId
        String taskId = pjp.getArgs()[1].toString();
        //获取任务信息
        TaskInfo taskInfo = manager.getTaskInfo(taskId);
        LOG.info("AsyncTaskMonitor is monitoring async task:{}", taskId);
        taskInfo.setStatus(TaskStatusEnum.RUNNING);
        manager.setTaskInfo(taskInfo);
        TaskStatusEnum status = null;
        try {
            pjp.proceed();
            status = TaskStatusEnum.SUCCESS;
        } catch (Throwable throwable) {
            status = TaskStatusEnum.FAILED;
            LOG.error("AsyncTaskMonitor:async task {} is failed.Error info:{}", taskId, throwable.getMessage());
        }
        taskInfo.setEndTime(new Date());
        taskInfo.setStatus(status);
        long diff=(taskInfo.getEndTime().getTime()-taskInfo.getStartTime().getTime());
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String totalTime = ""+diffDays+"天"+diffHours+"小时"+diffMinutes+"分"+diffSeconds+"秒";
        taskInfo.setTotalTime(totalTime);
        manager.setTaskInfo(taskInfo);
    }
}
