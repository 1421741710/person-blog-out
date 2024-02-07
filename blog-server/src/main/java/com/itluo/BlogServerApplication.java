package com.itluo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @EnableScheduling   //开启任务调度
 * @EnableTransactionManagement //开启注解方式的事务管理
 * @EnableCaching  //开发缓存注解功能
 * @author Administrator
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
@Slf4j
public class BlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServerApplication.class, args);
        log.info("server started");
    }

}
