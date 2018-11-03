package io.github.isliqian.runner;

import io.github.isliqian.cache.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * springboot 执行时默认执行一些方法
 */
@Component
public class MyRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);
    @Resource
    private RedisService redisService;
    /**
     * 清理缓存
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        logger.info("**************开始执行清理不必要缓存*************");
        //清理执行异步任务的缓存
        redisService.remove("college");
        logger.info("**************执行清理不必要缓存结束*************");
    }
}
