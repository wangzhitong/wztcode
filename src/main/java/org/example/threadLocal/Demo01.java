package org.example.threadLocal;


/**
 *  ThreadLocal 常用的4个方法
 *      new ThreadLocal()
 *      void set(T value)
 *      T get()
 *      void remove()
 *
 *  ThreadLocal和Synchronized的区别
 *      ThreadLocal采用“以空间换时间”的方式，为每个线程都提供了一份变量的副本，
 *      从而实现同时访问时不会互相干扰。侧重点：多个线程之间的数据相互隔离。并发性好
 *
 *      Synchronized采用“以时间换空间”的方式，只提供一份变量，让不同的线程排队访问，
 *      侧重点：多个线程之间访问资源的同步。
 *
 *  使用ThreadLocal的好处
 *      1.传递数据：保存每个线程绑定的数据，在需要的地方可以直接获取，避免参数直接传递带来的代码耦合问题
 *      2.线程隔离：各线程之间的数据相互隔离却又具备并发性，避免同步方式带来的性能损失。
 *
 *  弱引用和内存泄漏
 *      原因：ThreadLocal内存泄漏的根本原因是：由于ThreadLocalMap的声明周期跟Thread一样长，如果没有手动删除对应的key就会导致内存泄漏
 *      为什么使用弱引用：
 *          在ThreadLocalMap中的set/getEntry方法中，会对key为null（也就是ThreadLocal 为null）进行预判，
 *          如果为null的话，那么会对value置为null。
 *          这就意味着使用完ThreadLocal,CurrentThread依然运行的前提下，就算忘记调用remove方法，弱引用比强引用可以多一层保障，
 *          弱引用的ThreadLocal会被回收，对应的value在下一次ThreadLocalMap调用set/get/remove中的任一方法的时候会被清除，从而避免内存泄漏
 */
public class Demo01 {

    private String name;
    private String Content;

    ThreadLocal<String> t1 = new ThreadLocal<>();

    public Demo01() {
    }

    public Demo01(String name, String content) {
        this.name = name;
        Content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return t1.get();
    }

    public void setContent(String content) {
//        Content = content;
        t1.set(content);
    }

    public static void main(String[] args) {
        Demo01 demo01 = new Demo01();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                demo01.setContent(Thread.currentThread().getName() + "的数据");
                System.out.println("-------------------------------------");
                System.out.println(Thread.currentThread().getName() + "--->" + demo01.getContent());
            });
            thread.setName("线程" + i);
            thread.start();

        }
    }
}
