package org.example;

import org.example.entity.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream  中间操作
 */
public class StreamAPITest2 {

    static List<Employee> employees = Arrays.asList(
            new Employee("zhang", 20, 6666.00, Employee.Status.BUSY),
            new Employee("lis", 30, 7777.00, Employee.Status.FREE),
            new Employee("zhao", 25, 8800.00, Employee.Status.VOCATION),
            new Employee("tianqi", 12, 9900.00, Employee.Status.FREE)
    );

    //筛选与切片
    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(100, 200, 300, 400, 500);
        Stream<Integer> stream = list.stream();
        //判断大于300的元素
        stream.filter(s -> s > 300).forEach(System.out::println);

        //skip(n) 跳过元素，返回一个扔掉了前n个元素的流
        list.stream().skip(3).forEach(System.out::println);

        // distinct 去重
        list.stream().distinct().forEach((a) -> System.out.println(a));
    }

    /*
        映射
            map  接收Lambda,将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
            flatMap 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有的流连接成一个流。相当于数组的add和addAll,

              map (add)  [{a,a,a},{b,b,b}]   flatMap (addAll)  [a,a,a,b,b,b]
     */
    @Test
    public void test2() {
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        list.stream().map((s) -> s.toUpperCase()).forEach(System.out::println);
        list.stream().map(String::toUpperCase).forEach(System.out::println); //两种不同的处理方式 方法引用 和Lambda

        List<Employee> employeeList = Arrays.asList(new Employee("张三", 20), new Employee("list", 20));
        employeeList.stream().map(Employee::getName).forEach(System.out::println);

        // map
//        list.stream().map(StreamAPITest2::filterCharacter).forEach(sm -> sm.forEach(System.out::println));
//
//        Stream<Stream<Character>> objectStream = list.stream().map(new Function<String, Stream<Character>>() {
//            @Override
//            public Stream<Character> apply(String s) {
//                return filterCharacter(s);
//            }
//        });
//        objectStream.forEach(st -> st.forEach(System.out::println));


        // flatMap
        list.stream().flatMap(StreamAPITest2::filterCharacter).forEach(System.out::println);
    }


    public static Stream<Character> filterCharacter(String str) {

        ArrayList<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

    /*
     * 排序
     *  sorted()   自然排序
     *  sorted(Comparator com)  定制排序
     */
    @Test
    public void test3(){
        //自然排序
        List<String> strings = Arrays.asList("11", "22", "44", "33");
        strings.stream().sorted().forEach(System.out::println);


        List<Employee> list = Arrays.asList(new Employee("zhang", 20), new Employee("lis", 30), new Employee("zhao", 25));
        // 定制排序
        list.stream().sorted((e1,e2) -> {
           if (e1.getAge().equals(e2.getAge())){
               return e1.getName().compareTo(e2.getName());
           }else {
               return e1.getAge().compareTo(e2.getAge());
           }
        }).forEach(System.out::println);
        //forEach(employee -> System.out.println(employee));

    }

    /*
        终止操作
            查找与匹配
            allMatch    检查是否匹配所有元素
            anyMatch    检查是否至少匹配一个元素
            noneMatch   检查是否没有匹配所有元素
            findFirst   返回第一个元素
            findAny     返回当前流中的任意元素
            count       返回流中元素的总个数
            max         返回流中最大值
            min         返回流中最小值
     */
    @Test
    public void test4(){
        List<Employee> list = Arrays.asList(
                new Employee("zhang", 20, Employee.Status.BUSY),
                new Employee("lis", 30, Employee.Status.FREE),
                new Employee("zhao", 25, Employee.Status.VOCATION),
                new Employee("tianqi", 12, Employee.Status.FREE)
        );

        // boolean allMatch()
        boolean b = list.stream().allMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b);

        boolean b1 = list.stream().anyMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);

        boolean b2 = list.stream().noneMatch((e) -> e.getStatus().equals(Employee.Status.VOCATION));
        System.out.println(b2);

        //  Optional<T> findFirst();
//        Optional<Employee> first = list.stream().findFirst();
        // 返回排序后的第一个元素
        Optional<Employee> first = list.stream().sorted((e1, e2) -> -e1.getAge().compareTo(e2.getAge())).findFirst();
        System.out.println(first);

        //  Optional<T> findAny();
        // 如果有重复数据会出现多个元素，原因是 stream是串行流，会全部查找
        Optional<Employee> any = list.stream().filter((e) -> e.getStatus().equals(Employee.Status.FREE)).findAny();
        System.out.println(any.get());

        // 使用并行流解决重复数据，多个线程同时查找，以先找到的为准
        Optional<Employee> any1 = list.parallelStream().filter((e) -> e.getStatus().equals(Employee.Status.FREE)).findAny();
        System.out.println(any1.get());


        // long count()
        long count = list.stream().count();
        System.out.println("long count() --> " + count);

        // Optional<T> max()
        Optional<Employee> max = list.stream().max((e1, e2) -> {
            int i = e1.getAge().compareTo(e2.getAge());
            return i;
        });
        System.out.println("Optional<T> max() --> " + max.get());

        // Optional<T> min()
        Optional<Integer> min = list.stream().map(Employee::getAge).min(Integer::compare);
        System.out.println(min.get());

    }

    /*
        规约
        reduce （vi. 减少；缩小；归纳为）
        reduce(T identity,BinaryOperator) / reduce(BinaryOperator)  可以将流中元素返回结合起来，得到一个值
     */
    @Test
    public void test5(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 8, 9);
        //  reduce(T identity,BinaryOperator)  0代表初始值，（x,y）代表接口参数。第一次为 0 + x 值作为x + y，x+y的值在作为x,继续相加
        Integer sum = list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(sum);
        //简化写法
        Optional<Integer> reduce = list.stream().reduce(Integer::sum);
        System.out.println(reduce.get());


        Optional<Double> salary = employees.stream().map(Employee::getSalary).reduce(Double::sum);
        //第二种实现方式 Lambda方式: 若Lambda 体中的内容有方法已经实现了，我们可以使用“方法引用”
//        Optional<Double> salary = employees.stream().map((e) -> e.getSalary()).reduce((x, y) -> x + y);

        System.out.println(salary.get());

    }

    /*
     *  收集
     *      collect 将流转换为其他形式，接收一个Collector接口的实现，用于Stream中元素做汇总的方法
     *      java8 提供了Collectors 工具类，实现了大部分常用方法
     */
    @Test
    public void test6(){
        List<String> collect = employees.stream().map(Employee::getName).collect(Collectors.toList());
        collect.forEach(System.out::println);

        //收集名字 并转为Set
        Set<String> collect1 = employees.stream().map(Employee::getName).collect(Collectors.toSet());
        collect1.forEach(System.out::println);

        //收集名字 并转为HashSet
        HashSet<String> collect2 = employees.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        collect2.forEach(System.out::println);

    }
    @Test
    public void test7(){
        //获取总数
        Long count = employees.stream().collect(Collectors.counting());
        System.out.println(count);

        //获取平均值
        Double avg = employees.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        //获取总和
        Double sum = employees.stream().collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        //最大值   -- 工资最大的人员
        Optional<Employee> max = employees.stream().collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(max.get());

        //最高工资
        Optional<Double> maxSalary = employees.stream().map(Employee::getSalary).collect(Collectors.maxBy(Double::compare));

        //最小值  最低工资
        Optional<Double> min = employees.stream().map(Employee::getSalary).collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());

    }
    @Test
    public void test8(){
        //分组
        Map<Employee.Status, List<Employee>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(collect);

        //多级分组   先按状态分再按年龄分
        Map<Employee.Status, Map<String, List<Employee>>> collect1 = employees.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
            if (e.getAge() <= 20) {
                return "青年";
            } else if (e.getAge() <= 30) {
                return "中年";
            } else {
                return "老年";
            }
        })));
        System.out.println(collect1);

        //分区  按照true和false 分区
        Map<Boolean, List<Employee>> collect2 = employees.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() > 8000));
        System.out.println(collect2);

    }
    @Test
    public void test9(){
        //Collectors.summarizingDouble   获取数据的所有基本信息，最大最小值，平均值，总数量等
        DoubleSummaryStatistics collect = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(collect.getMax());
        System.out.println(collect.getAverage());
        System.out.println(collect.getCount());

        //添加 给输出结果添加元素
        String collect1 = employees.stream().map(Employee::getName).collect(Collectors.joining(",","->","<-"));
        System.out.println(collect1);
    }


}
