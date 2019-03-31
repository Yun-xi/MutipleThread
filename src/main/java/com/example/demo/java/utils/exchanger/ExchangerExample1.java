package com.example.demo.java.utils.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerExample1 {

    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start.");
                try {
                    String result = exchanger.exchange("I am from T-A");
                    System.out.println(Thread.currentThread().getName() + " Get value [" + result + "]");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end.");
            }
        }, "==AA==").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start.");
                try {
                    TimeUnit.SECONDS.sleep(20);
                    String result = exchanger.exchange("I am from T-B");
                    System.out.println(Thread.currentThread().getName() + " Get value [" + result + "]");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end.");
            }
        }, "==BB==").start();
    }
}
