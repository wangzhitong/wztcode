package com.atguigu.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 开启缓存步骤
 * 1.开启基于注解的缓存 @EnableCaching
 * 2.标注缓存注解既可
 * @Cachable
 * @CacheEvict
 * @CachePut
 *
 * 整合redis
 *      redis缓存，用CacheManager创建Cache缓存组件来给缓存中存取数据
 *      1.当我们引入Redis的starter时，容器中保存的就是RedisCacheManager
 *      2.RedisCacheManager帮我们创建RedisCache来作为缓存组件，RedisCache通过redis操作缓存数据
 */
@MapperScan(value = "com.atguigu.cache.mapper")
@EnableCaching
@SpringBootApplication
public class Springboot01CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot01CacheApplication.class, args);
    }

}
