package org.example;

import org.example.entity.Employee;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;


/**
 * 方法引用：若Lambda 体中的内容有方法已经实现了，我们可以使用“方法引用”
 *      （方法引用就是Lambda的另一种表现形式）
 * 主要有三种语法格式
 *  对象 :: 实例方法名
 *  类 :: 静态方法名
 *  类 :: 实例方法名
 *
 * 注意：
 *      Lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致。
 *      若Lambda 参数列表中的第一个参数是实例方法的调用者，而第二个参数是实例方法的参数时，可使用 ClassName :: method
 *      例：（String :: equals）
 */
public class LambdaMethadRef {

    @Test
    public void test1(){
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {

            }
        };

        Consumer<String > consumer1 = s -> System.out.println(s);

        consumer1.accept("Lambda  方式1");
        PrintStream out = System.out;
        Consumer<String> consumer2 = out :: println; //System.out :: println

        consumer2.accept("Lambda 方式二 方法引用。。。");
    }

    @Test
    public void test(){
        Employee employee = new Employee("zhangsan",20);
        // 方式一
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return null;
            }
        };

        // 方式二 Lambda
        // Supplier<String> supplier = () -> employee.getName();

        //方式三 方法引用
        Supplier<String> supplier1 = employee :: getName;
        String s = supplier1.get();
        System.out.println(s);

    }

    //类 :: 静态方法名
    @Test
    public void test3(){

        // 方式一  原始方式
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        };

        //方式二  Lambda 方式
        Comparator<Integer> com1 = (o1, o2) -> Integer.compare(o1,o2);

        //方法三 方法引用
        Comparator<Integer> com2 = Integer::compare;

    }

    // 类::实例方法名
    @Test
    public void test4(){
        BiPredicate<String,String> bp = (s, s2) -> s.equals(s2);

        BiPredicate<String,String> bp1 = String::equals;

        boolean test = bp1.test("aa", "aa");
        System.out.println(test);
    }

    //构造器引用
    // 需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致
    @Test
    public void test5(){
        Supplier<Employee> sup = () -> new Employee();
        Employee employee = sup.get();
        System.out.println(employee);

        // 构造器引用
        Supplier<Employee> sup2 = Employee::new;
        Employee employee1 = sup2.get();
        System.out.println(employee1);

//        Supplier<Employee> sup3 = new Supplier<Employee>() {
//            @Override
//            public Employee get() {
//                return new Employee();
//            }
//        };

        BiFunction<String,Integer,Employee> biFunction = Employee::new;
        // 相当于调用带参数的构造器  apply(U,T)
        Employee lisi = biFunction.apply("lisi", 30);
        System.out.println(lisi);


        BiFunction<String,Integer,Employee> biFunction2 = (s, integer) -> new Employee(s,integer);

        Employee zhangsan = biFunction2.apply("zhangsan", 20);
        System.out.println(zhangsan);

    }

    //数组引用  Type[] :: new;
    @Test
    public void test7(){
        Function<Integer,String[]> fun = x -> new String[x];
        String[] apply = fun.apply(10);
        System.out.println(apply.length);

        Function<Integer,String []> fun2 = String[] :: new;
        String[] apply1 = fun2.apply(20);
        System.out.println(apply1.length);
    }

}
