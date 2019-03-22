package com.example.demo.java.singleton;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-03-18 16:23
 */
public class Singleton5 {

    private Singleton5() {

    }

    public static class InstanceHolder {
        private final static Singleton5 instance = new Singleton5();
    }

    public static Singleton5 getInstance() {
        return InstanceHolder.instance;
    }
}
