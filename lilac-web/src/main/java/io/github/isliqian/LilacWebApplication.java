package io.github.isliqian;



import io.github.isliqian.log.bean.LoggerMessage;
import io.github.isliqian.log.queue.LoggerQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootApplication(scanBasePackages = {"io.github.isliqian.*",
        "io.github.isliqian.cache.*",
        "io.github.isliqian.log.*",
        "io.github.isliqian.shiro.*",
        "io.github.isliqian.sys.*",
        "io.github.isliqian.mail.*",
        "io.github.isliqian.mybatisgenerator.*",
        "io.github.isliqian.splider.*",
        "io.github.isliqian.scheduled.*"})
@EnableScheduling
@EnableCaching
@EnableAsync
@EnableRedisHttpSession//session存储在redis
@EnableWebSocketMessageBroker
public class LilacWebApplication {
    private Logger logger = LoggerFactory.getLogger(LilacWebApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(LilacWebApplication.class, args);
    }
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 推送日志到/topic/pullLogger
     */
    @PostConstruct
    public void pushLogger(){
        ExecutorService executorService= Executors.newFixedThreadPool(2);
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        LoggerMessage log = LoggerQueue.getInstance().poll();
                        if(log!=null){
                            if(messagingTemplate!=null)
                                messagingTemplate.convertAndSend("/topic/pullLogger",log);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executorService.submit(runnable);
        executorService.submit(runnable);
    }


}
