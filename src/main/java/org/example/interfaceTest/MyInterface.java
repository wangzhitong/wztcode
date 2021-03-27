package org.example.interfaceTest;



/**
 * java8 在接口中可以声明默认方法，和静态方法，
 *
 * 子类可以直接调用，如果实现多个接口中有相同的方法，子类可以选择调用，如果有父类则使用父类的方法
 */
public interface MyInterface {

    default String getName(){
        return "嘿嘿嘿";
    }

    static String staticMethod(){
        return "接口中的静态方法";
    }
}
