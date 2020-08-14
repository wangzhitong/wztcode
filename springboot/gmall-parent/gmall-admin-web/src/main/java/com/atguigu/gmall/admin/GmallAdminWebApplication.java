package com.atguigu.gmall.admin;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * exclude = {DataSourceAutoConfiguration.class}
 * 表示不进行数据源的自动配置
 *
 * 如果导入的依赖，引入了一个自动配置场景，这个场景自动配置默认生效，我们就必须配置，
 * 如果不想配置，可以在引入时排除这个场景的依赖，或者排除掉这个场景的自动配置类。
 *
 */
@EnableDubbo
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GmallAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallAdminWebApplication.class, args);
    }

}
