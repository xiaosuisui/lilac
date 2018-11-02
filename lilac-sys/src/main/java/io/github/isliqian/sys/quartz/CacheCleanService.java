package io.github.isliqian.sys.quartz;

import io.github.isliqian.cache.service.RedisService;
import io.github.isliqian.sys.constant.SysConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时任务，执行清理缓存
 */
@Component
public class CacheCleanService {
    private static final Logger logger = LoggerFactory.getLogger(CacheCleanService.class);

    @Resource
    private RedisService redisService;

    //每天凌晨1点执行清理缓存
    @Scheduled(cron="0 0 1 * * *")
    @Async //异步清除
    public void clean() {
        logger.info("现在时间:" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ",开始执行定时清理缓存操作......");

        redisService.remove(SysConstant.CACHE_AREA_LIST);

        redisService.remove(SysConstant.CACHE_DICT_LIST);

        redisService.remove(SysConstant.CACHE_MENU_LIST);

        redisService.remove(SysConstant.CACHE_OFFICE_LIST);

        redisService.remove(SysConstant.CACHE_DEPARTMENT_LIST);

        redisService.remove(SysConstant.CACHE_ROLE_LIST);

        redisService.remove(SysConstant.CACHE_USER_LIST);

        logger.info("现在时间:" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ",执行定时清理缓存操作完成......");


    }
}
