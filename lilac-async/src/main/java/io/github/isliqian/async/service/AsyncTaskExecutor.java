package io.github.isliqian.async.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by shirukai on 2018/7/31
 * 异步任务执行器
 */
@Component
public class AsyncTaskExecutor {
    private static Logger LOG = LoggerFactory.getLogger(AsyncTaskExecutor.class);

    @Async
    public void executor(AsyncTaskConstructor asyncTaskGenerator, String taskInfo) {
        LOG.info("AsyncTaskExecutor is executing async task:{}", taskInfo);
        asyncTaskGenerator.async();
    }

}
