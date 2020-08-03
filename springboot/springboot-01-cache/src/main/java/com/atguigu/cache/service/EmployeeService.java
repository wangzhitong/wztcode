package com.atguigu.cache.service;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

//可以用@CacheConfig(cacheNames = "emp")把类里面缓存的公共配置提取出来，方法上的缓存注解中不需要在加上value属性
@CacheConfig(cacheNames = "emp")
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;


    /**
     * 缓存说明
     * 将方法运行结果进行缓存，
     * CacheManager管理多个Cache组件，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件都有一个唯一的名字
     * 缓存的属性：
     *  cacheNames和value : 指定缓存组件的名字,cacheNames可以指定多个名字{}
     *  key : 缓存数据使用的key，可以用它来指定。默认使用的是方法参数的值。参数-方法返回值
     *      key的写法可以是#id(参数ID的值)，#a0 #p0 "#root.args[0]"等
     *  keyGenerator:key的生成器，可以自己指定key的生成器的组件ID
     *      key/keyGenerator 二者选择一个使用
     *  cacheManager ：指定缓存管理器，或者cacheResolver指定获取解析器
     *  condition : 指定符合条件的情况下才缓存
     *  unless : 否定缓存，当unless指定的条件为true时，方法返回值不会被缓存，为false时进行缓存
     *         可以获取到结果进行判断，unless="#result==null"
     *
     *  sync : 使用使用异步模式
     *
     *  condition = "#root.args[0]>1" 满足条件id>1时才进行缓存
     *  unless = "#a0>1" 满足条件id>1时不缓存
     *
     *  开启缓存后，自动配置类CacheAutoConfiguration会给容器容添加很多缓存的配置类
     *  默认生效的是“SimpleCacheConfiguration” 他会给容器中注册一个CacheManager: 名字是ConcurrentMapCacheManger
     *  可以获取和创建ConcurrentMapCache类型的缓存组件，将他们的数据保存在ConcurrentMap中.
     *  运行流程：
     * @Cacheable:
     *      1.方法运行前，先去查询Cache(缓存组件)，按照cacheNames指定的名字获取。
     *          （CacheManager先获取相应的缓存），第一次获取缓存，如果没有Cache组件就会根据cacheNames的名字自动创建。
     *      2.去Cache中查找缓存的内容，使用一个key，默认就是方法的参数；
     *          key是按照某种生成策略生成的，默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key；
     *          SimpleKeyGenerator生成key的策略：
     *              如果方法上没有参数：key = new SimpleKey();
     *              如果有一个参数：key = 参数的值;
     *              如果有多个参数：key = SimpleKey(params);
     *      3.没有查到缓存就调用目标方法；
     *      4.将目标方法的返回结果放进缓存中。
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "emp"/*,unless = "#a0>1"*/)
    public Employee getEmp(Integer id){
        System.out.println("查询"+id+"号员工");
        return employeeMapper.getEmpByID(id);
    }

    /**
     * @CachePut:即调用方法，又更新缓存数据
     * 运行时机，先调用方法，再把目标方法的返回值进行缓存
     *
     * 更新操作时缓存的key要和查询操作的key一样，否则查询不到更新后的缓存数据
     *
     * @param employee
     * @return
     */
    @CachePut(value = "emp",key = "#result.id")
    public Employee updateEmp(Employee employee){
        employeeMapper.updateEmp(employee);
        return employee;
    }


    /**
     * 清除缓存
     * key 指定要清除的缓存
     * allEntries = true 指定清除这个缓存中的所有数据
     * beforeInvocation = false 缓存的清除是否在方法执行前执行
     *      默认缓存清除操作是在方法执行之后执行，如果出现异常缓存就不会清除。
     * beforeInvocation = true 代表清除缓存操作是在方法运行之前执行，无论方法是否出现异常，缓存都被清除。
     * @param id
     */
    @CacheEvict(value = "emp" ,key = "#id",beforeInvocation = true)
    public void delEmp(Integer id){
        System.out.println("删除ID为"+id+"的员工");
//        employeeMapper.delEmp(id);
        int i= 10/0;
    }


    /**
     * 复杂缓存设置
     * @param lastName
     * @return
     */
    @Caching(
        cacheable = {
                @Cacheable(value = "emp",key = "#lastName")
        },
        put = {
                //@CachePut 表示这个方法一定会执行
                @CachePut(value = "emp",key = "#result.id"),
                @CachePut(value = "emp",key = "#result.email")
        }
    )
    public Employee getEmpByLaseName(String lastName){
        return employeeMapper.getEmpByLaseName(lastName);
    }
}
