package org.example;

import org.example.entity.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Stream关注的是对数据的运算，与CPU打交道
 *  集合关注的是数据的存储，与内存打交道
 *
 *  使用streamAPI可以对内存中的数据进行过滤(filter())、排序(sort()\sorted())、映射(map()\flatMap())、规约(reduce)、收集(collect)等操作.
 *  类似于sql对数据库中表的相关操作
 *
 *  Stream 自己不会存储元素
 *  Stream 不会改变源对象，相反，他们会返回一个持有结果的新的Stream
 *  Stream 操作是延迟执行的。这意味着他们会等到需要结果是才执行。
 *
 *
 * 1.Stream 的三个操作步骤
 *      创建Stream
 *
 *      中间操作
 *
 *      终止操作
 *          一旦执行中止操作，就执行中间操作链，并产生结果，之后这个Stream 就不能再被使用。
 */
public class StreamAPITest {


    //创建Stream
    @Test
    public void test1(){
        // 1.可以通过Collection 系列集合提供的stream() 或 parallelStream()
        ArrayList<String> arrayList = new ArrayList<>();
        Stream<String> stream = arrayList.stream();

        //2.通过Arrays中的静态方法stream() 获取数组流
        Employee[] emps = new Employee[10];
        Stream<Employee> stream1 = Arrays.stream(emps);

        //3.通过Stream类中的静态方法of()
        Stream<String> Stream2 = Stream.of("a", "b");

        //创建无限流
        Stream<Integer> iterate = Stream.iterate(0, x -> x + 2);
//        iterate.limit(10).forEach(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) {
//                System.out.println(integer);
//            }
//        });
//        iterate.limit(10).forEach(integer -> System.out.println(integer));
        iterate.limit(10).forEach(System.out::println);

        //生成
        Stream<Double> generateStream = Stream.generate(new Supplier<Double>() {

            @Override
            public Double get() {
                return Math.random();
            }
        });
        // Lambda
        Stream<Double> generateStream2 = Stream.generate(() -> Math.random());
        //方法引用
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }
}
