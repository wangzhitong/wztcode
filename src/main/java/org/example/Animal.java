package org.example;

/**
 * 自定义函数式接口
 * 只包含一个抽象方法的接口成为函数式接口
 * @FunctionalInterface注解用来显式的标注该接口为一个函数式接口，用作验证
 * 
 * @author wangzhitong
 *
 */
@FunctionalInterface
public interface Animal {

	
	String eat(String food);
	
//	void run();
}
