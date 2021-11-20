package com.hyperj.framework.config.shiro;

import org.springframework.stereotype.Component;

@Component
public class ThreadLocalToken {

    /**
     * ThreadLocal提供了线程内存储变量的能力，这些变量不同之处在于每一个线程读取的变量是对应的互相独立的。
     * 通过get和set方法就可以得到当前线程对应的值
     *
     * ThreadLocal 与 Synchronized区别
     * 相同：ThreadLocal和线程同步机制都是为了解决多线程中相同变量的访问冲突问题。
     * 不同：Synchronized同步机制采用了“以时间换空间”的方式，仅提供一份变量，让不同的线程排队访问；而ThreadLocal采用了“以空间换时间”的方式，每一个线程都提供了一份变量，因此可以同时访问而互不影响。
     *
     * 以时间换空间->即枷锁方式，某个区域代码或变量只有一份节省了内存，但是会形成很多线程等待现象，因此浪费了时间而节省了空间。
     * 以空间换时间->为每一个线程提供一份变量，多开销一些内存，但是呢线程不用等待，可以一起执行而相互之间没有影响。
     *
     * 小结：ThreadLocal是解决线程安全问题一个很好的思路，它通过为每个线程提供一个独立的变量副本解决了变量并发访问的冲突问题。在很多情况下，ThreadLocal比直接使用synchronized同步机制解决线程安全问题更简单，更方便，且结果程序拥有更高的并发性。
     */
    private ThreadLocal<String> local = new ThreadLocal<>();

    public void setToken(String token){
        local.set(token);
    }

    public String getToken(){
        return local.get();
    }

    public void clear(){
        local.remove();
    }
}
