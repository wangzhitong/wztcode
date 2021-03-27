package org.example;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTimeTest {

    // java8 日期类
    // localDate    LocalTime   LocalDateTime
    //    日期         时间        日期和时间
    // 日期类的用法一致、
    @Test
    public void test1(){

        //获取当前时间  2020-12-04
        LocalDate now = LocalDate.now();
        System.out.println(now);

        //获取指定时间的LocalDate对象
        LocalDate of = LocalDate.of(2020, 12, 4);
        System.out.println(of);

        //获取当天时间的0分0秒
        LocalDate localDate1 = LocalDate.now();
        LocalDateTime localDateTime = localDate1.atStartOfDay();
        System.out.println(localDateTime);

        //获取时间戳
        Instant now1 = Instant.now();
        System.out.println(now1);
    }

    @Test
    public void test2(){

        LocalTime now2 = LocalTime.now();
        System.out.println(now2);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime localDateTime = now.plusMonths(3); //在原来日期上+3天
        LocalDateTime x = localDateTime.minusDays(5);//在原来日期上-5天
        System.out.println(x);
    }

    //Instant   时间戳（以Unix 元年：1970年1月1日 00:00:00到某个时间之间的毫秒值）
    @Test
    public void test3(){
        Instant now = Instant.now();
        //设置偏移量  当前时间+8个小时
        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);
        System.out.println(now.toEpochMilli()); //获取毫秒值

        Instant instant = Instant.ofEpochSecond(60);
        System.out.println(instant);
    }

    // Duration 计算两个时间之间的范围
    // Period  计算两个日期之间的间隔
    @Test
    public void test4() throws InterruptedException {
        Instant now = Instant.now();

        Thread.sleep(2000);

        Instant now1 = Instant.now();
        //所有日期类都可以通过 Duration.between 来计算时间间隔
        Duration between = Duration.between(now, now1);
        System.out.println(between.toMillis());
    }
    // Period  计算两个日期之间的间隔
    @Test
    public void test5() throws InterruptedException {
        LocalDate now = LocalDate.of(2020,10,30);
        Thread.sleep(1000);
        LocalDate now1 = LocalDate.now();

        Period between = Period.between(now, now1);
        System.out.println(between);  // P1M5D
        System.out.println(between.getYears()+" "+between.getMonths()+" "+between.getDays()); //0 1 5

    }

    //   时间校正器 TemporalAdjuster
    @Test
    public void test6(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.withDayOfMonth(10);
        System.out.println(localDateTime);

        //TemporalAdjusters 工具类
        LocalDateTime with = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)); // 获取下一个周日的日期
        System.out.println(with);

        //自定义下一个工作日
        LocalDateTime dt = now.with((l) -> {
            LocalDateTime dateTime = (LocalDateTime) l;
            DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return dateTime.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return dateTime.plusDays(2);
            } else {
                return dateTime.plusDays(1);
            }
        });
        System.out.println(dt);
    }

    // 格式化  DateTimeFormatter
    @Test
    public void test7(){
        DateTimeFormatter isoDateTime = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime now = LocalDateTime.now();
        String format = isoDateTime.format(now);
        System.out.println(format); //日期对象和格式化对象都可以调用format方法
        System.out.println(now.format(isoDateTime));

        // 自定义格式化规则  ofPattern
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String format1 = dateTimeFormatter.format(now);
        System.out.println(format1);

        LocalDateTime parse = now.parse(format1, dateTimeFormatter);
        System.out.println(parse);

    }
}
