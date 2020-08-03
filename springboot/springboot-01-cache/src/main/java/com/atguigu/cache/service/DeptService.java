package com.atguigu.cache.service;

import com.atguigu.cache.bean.Department;
import com.atguigu.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class DeptService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Qualifier("deptCacheManager")
    @Autowired
    RedisCacheManager deptCacheManager;

    /**
     * 缓存的数据能存入redis,第二次从缓存中查询时就不能反序列化回来。
     * 因为默认使用的CacheManager是RedisTemplate<Object, Employee> employeeRedisTemplate
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "dept",cacheManager = "deptCacheManager")
    public Department getDeptById(Integer id){
        System.out.println("查询部门" + id);
        Department department = departmentMapper.getDeptById(id);
        return department;
    }

    //不适用注解的情况下使用缓存
    public Department getDeptById1(Integer id){
        Department department = departmentMapper.getDeptById(id);
        Cache dept = deptCacheManager.getCache("dept");
        dept.put("dept:1",department);
        return department;
    }
}
