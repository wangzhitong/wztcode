package com.atguigu.cache;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01CacheApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;  //操作k-v都是字符串

    @Autowired
    RedisTemplate redisTemplate;  //操作k-v都是对象

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RedisTemplate<Object,Employee> empRedisTemplate;

    @Test
    public void testRedis01() {
        stringRedisTemplate.opsForValue().append("msg","world");
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);
    }

    @Test
    public void testRedisObj(){
        Employee empByID = employeeMapper.getEmpByID(1);
        //保存对象默认是用的是jdk的序列化机制，序列化后数据保存到Redis中，但内容不可查看
//        redisTemplate.opsForValue().set("emp01",empByID);
        //我们可以将对象转为json后在进行保存
        empRedisTemplate.opsForValue().set("emp01",empByID);
    }

}
