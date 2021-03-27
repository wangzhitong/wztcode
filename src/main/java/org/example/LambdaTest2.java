package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Test;

/**
 * java 内置的4大核心函数式接口
 *	
 *	消费型接口 Consumer<T> void accept(T t)
 *	供给型接口 Supplier<T> T get()
 *	函数型接口 Function<T,R> R apply(T t)
 *	断定型接口 Predicate boolean test(T t)
 *
 * 	什么时候使用给定的函数式接口？
 * 		如果我们开发中需要定义一个函数式接口，首先看看在已有的jdk提供的函数式接口是否提供了能满足需求的
 * 		函数式接口，如果有，直接调用即可，不需要自己在定义。
 */
public class LambdaTest2 {

	@Test
	public void test1() {
		
		happyTime(400,mony-> System.out.println("----------"+mony));
	}
	
	public void happyTime(double mony, Consumer<Double> con) {
		con.accept(mony);
	}
	
	
	@Test
	public void test2() {
		
		List<String> asList = Arrays.asList("11","12","13","14","22","23","24");
		List<String> filterString = filterString(asList, new Predicate<String>() {

			@Override
			public boolean test(String t) {
				// TODO Auto-generated method stub
				return t.contains("1");
			}
		});
		System.out.println(filterString);
		
		System.out.println("Lambda 方式----------------------------");
		
		List<String> filterString2 = filterString(asList,s -> s.contains("2"));
		
		System.out.println(filterString2);
		
	}
	
	public List<String> filterString(List<String> list,Predicate<String> pre){
		
		ArrayList<String> arrayList = new ArrayList<String>();
		for (String string : list) {
			boolean test = pre.test(string);
			if(test) {
				arrayList.add(string);
			}
			
		}
		return arrayList;
		
	}
}
