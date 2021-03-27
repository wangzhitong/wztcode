package org.example;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamAPITest3 {

    @Test
    public void test1(){
        Integer[]  nums = new Integer[]{1,2,3,4,5};
        Arrays.stream(nums).map(x -> x * x).forEach(System.out::println);
    }

    /**
     * java8 并行流
     */
    @Test
    public void test2(){
        Instant now = Instant.now();

        // parallel() 并行流   sequential() 串行流
        long reduce = LongStream.rangeClosed(0, 100000000000L).parallel().reduce(0, (x, y) -> x + y); //8656
//        LongStream.rangeClosed(0, 100000000000L).sequential().reduce(0,Long::sum);  //35785
        System.out.println(reduce);

        Instant end = Instant.now();

        System.out.println("耗费时间为：" + Duration.between(now,end).toMillis());


    }
}
