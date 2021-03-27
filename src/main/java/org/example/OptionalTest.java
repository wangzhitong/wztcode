package org.example;

import org.example.entity.Employee;
import org.example.entity.Godness;
import org.example.entity.Man;
import org.junit.Test;

import java.util.Optional;

public class OptionalTest {

    /*
        Optional 容器类的常用方法：
            Optional.of(T t)：创建一个Optional实例
            Optional.empty()：创建一个空的Optional实例
            Optional.ofNullable(T t)：若t不为null,创建Optional实例，否则创建空实例
            isPresent()：判断是否包含值
            orElse(T t)：如果调用对象包含值，返回该值，否则返回t
            orElseGet(Supplier s)：如果调用对象包含值，返回该值，否则返回s获取的值
            map(Function f)：如果有值对其处理，返回处理后的Optional，否则返回Optional.empty()
            flatMap(Function mapper)：与map类似，要求返回值必须是Optional
     */

    @Test
    public void test(){
        Optional<Employee> employee = Optional.of(new Employee());
        System.out.println(employee.get());

        Optional<Object> o = Optional.of(null);
        System.out.println(o);
    }

    @Test
    public void test2(){
        //创建一个空的Optional实例。但是不能调用get方法
        Optional<Object> empty = Optional.empty();
        System.out.println(empty);
        System.out.println(empty.get());
    }

    @Test
    public void test3(){
        //Optional.ofNullable(T t)：若t不为null,创建Optional实例，否则创建空实例
        // 相当于 of 和 empty 的结合
        Optional<Employee> employee = Optional.ofNullable(null);
//        System.out.println(employee.get());
        // isPresent()：判断是否包含值
        if(employee.isPresent()){
            System.out.println(employee.get());
        }
    }

    @Test
    public void test4(){
        //orElse(T t)：如果调用对象包含值，返回该值，否则返回t
//        Optional<Employee> employee = Optional.ofNullable(new Employee());
        Optional<Employee> employee = Optional.ofNullable(null);

//        Employee employee1 = employee.orElse(new Employee("z", 20, Employee.Status.BUSY));
//        System.out.println(employee1);

        //orElseGet(Supplier s) 功能与orElse一样，只是参数变成了Supplier 接口，可以实现更多要求
        Employee employee1 = employee.orElseGet(() -> new Employee("zhaoliu", 20, Employee.Status.BUSY));
        System.out.println(employee1);
    }

    @Test
    public void test5(){
        //map(Function f)：如果有值对其处理，返回处理后的Optional，否则返回Optional.empty()
        //flatMap(Function mapper)：与map类似，要求返回值必须是Optional

        Optional<Employee> employee = Optional.ofNullable(new Employee("zhaoliu", 20, Employee.Status.BUSY));
//        Optional<String> s = employee.map(e -> e.getName());
//        System.out.println(s.get());

        // 返回值必须要Optional
        Optional<Double> aDouble = employee.flatMap(e -> Optional.ofNullable(e.getSalary()));
        System.out.println(aDouble);

    }
    //例子
    @Test
    public void test6(){
//        Collections
        Optional<Godness> godness = Optional.ofNullable(new Godness("boduolaos"));
        Optional<Man> optionalMan = Optional.ofNullable(new Man(godness));
        String goddessName = getGoddessName(optionalMan);
        System.out.println(goddessName);

    }

    public String getGoddessName(Optional<Man> man){
        return man.orElse(new Man())
                .getGodness()
                .orElse(new Godness("canglaos"))
                .getName();
    }
}
