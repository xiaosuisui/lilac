package io.github.isliqian;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = {"io.github.isliqian.*", "io.github.isliqian.log.*", "io.github.isliqian.shiro.*","io.github.isliqian.sys.*"})
@EnableScheduling
public class LilacWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LilacWebApplication.class, args);
    }

}
