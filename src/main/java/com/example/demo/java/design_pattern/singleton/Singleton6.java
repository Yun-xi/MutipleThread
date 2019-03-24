package com.example.demo.java.design_pattern.singleton;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-03-18 16:26
 */
public class Singleton6 {

    private Singleton6() {}

    private enum Singleton {
        INSTANCE;

        private final Singleton6 instance;

        Singleton() {
            instance = new Singleton6();
        }

        public Singleton6 getInstance() {
            return instance;
        }
    }

    public static Singleton6 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }
}
