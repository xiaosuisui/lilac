package io.github.isliqian;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication(scanBasePackages = {"io.github.isliqian.*", "io.github.isliqian.cache.*","io.github.isliqian.log.*", "io.github.isliqian.shiro.*","io.github.isliqian.sys.*","io.github.isliqian.mail.*","io.github.isliqian.mybatisgenerator.*"})
@EnableScheduling
@EnableCaching
@EnableAsync
@EnableRedisHttpSession//session存储在redis
public class LilacWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LilacWebApplication.class, args);
    }



}
