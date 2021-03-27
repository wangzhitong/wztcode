package org.example;

import java.util.Comparator;

import org.junit.Test;

public class LambdaTest1 {

	
	@Test
	public void test1() {

		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Runnable 匿名内部类");
			}
		};
		r.run();
		
//		Thread t1 = new Thread(r);
//		t1.start();
		
		System.out.println("*****************Lambda*****************");
		
		Runnable r2  = ()-> System.out.println("Lambda 方式，实现匿名内部类");
		r2.run();
	}
	
	
	/**
	 * Lambda 表达式的使用:
	 * 1.举例：(o1,o2) -> Integer.compare(o1,o2);
	 * 2.格式：左边为形参列表，也就是接口中的抽象方法的形参列表
	 * 		     右边为Lambda体，其实就是重写的抽象方法的方法体
	 * 		     注：Lambda只能用于只有且只有一个抽象方法的接口。
	 * 3.Lambda 表达式的使用：（分为6中情况）
	 * 
	 * 	总结：
	 * 		左边Lambda 形参列表的参数类型可以省略，如果Lambda 形参列表只有一个参数，其括号也可以省略
	 * 		右边Lambda 体应该使使用一对{}包裹，如果Lambda体只有一条执行语句（可能是return语句），那么{}和return都可以省略
	 * 
	 * 4.Lambda 表达式的本质：作为函数式接口的实例
	 * 5.如果接口中，只声明了一个抽象方法，此接口就成为函数式接口。
	 * 6.以前用匿名实现类表示的现在都可以用Lambda表达式来写。
	 */
	@Test
	public void test2() {
		Animal a = new Animal() {
			
//			@Override
//			public void eat() {
//				// TODO Auto-generated method stub
//				
//			}

			@Override
			public String eat(String food) {
				// TODO Auto-generated method stub
				return food;
			}
			
		};
		
		Animal a2 = (food)-> {
			return food;
		};
//		Animal a2 = (food)-> food;  函数体内只有一条return 语句时{}和return 都可以省略
		String eat = a2.eat("猫粮");
		System.out.println(eat);
		
		// 返回一个变量时，{}不能省略   或者  Animal a5 = food-> food;
		Animal a3 = (food)-> {return food;};
	}
	
	
	// 语法格式四：Lambda 若只需要一个参数时，参数的小括号可以省略
	@Test
	public void test5() {
		
		Animal a2 = food-> {
			return food;
		};
		String eat = a2.eat("猫粮");
		System.out.println(eat);
	}
	
	//语法格式五：Lambda 需要两个或以上的参数，多条执行语句，并且可以有返回值	
	@Test
	public void test6() {
		Comparator<Integer> com1 = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1, o2);
			}
		};
		
		Comparator<Integer> com2 = (o1,o2) -> {
			System.out.println(o1);
			System.out.println(o2);
			return Integer.compare(o1, o2);
		};
		
		System.out.println( com2.compare(10, 23));
	}
	
	//语法格式六：Lambda体中如果只有一行代码（可能是return语句），那么可以省略{}和return，	如果是return语句 return和 {}必须都要省略
	@Test
	public void test7() {
		
		Comparator<Integer> com1 = (o1,o2) -> Integer.compare(o1, o2);
		System.out.println(com1.compare(22, 33));
	}
}
