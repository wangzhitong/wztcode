package org.example.interfaceTest;

public class Test {

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        System.out.println(myClass.getName());

        System.out.println(MyInterface.staticMethod());
    }
}
