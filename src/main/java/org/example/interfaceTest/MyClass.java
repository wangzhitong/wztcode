package org.example.interfaceTest;

public class MyClass /*extends SubClass*/ implements MyInterface,MyInterface2 {

    @Override
    public String getName() {

        return MyInterface.super.getName();
    }
}
