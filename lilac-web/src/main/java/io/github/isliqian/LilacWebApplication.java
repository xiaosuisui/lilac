package io.github.isliqian;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;


@SpringBootApplication(scanBasePackages = {"io.github.isliqian.*", "io.github.isliqian.log.*", "io.github.isliqian.shiro.*","io.github.isliqian.sys.*"})
@EnableScheduling
@EnableCaching
public class LilacWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LilacWebApplication.class, args);
    }



}
