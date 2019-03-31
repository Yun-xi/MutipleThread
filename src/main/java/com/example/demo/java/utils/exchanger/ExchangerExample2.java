package com.example.demo.java.utils.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ExchangerExample2 {

    public static void main(String[] args) {
        final Exchanger<Integer> exchanger = new Exchanger<>();

        new Thread() {
            @Override
            public void run() {
                AtomicReference<Integer> value = new AtomicReference<>(1);

                try {
                    while (true) {
                        value.set(exchanger.exchange(value.get()));
                        System.out.println("Thread A has Value : " + value.get());
                        TimeUnit.SECONDS.sleep(3);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                AtomicReference<Integer> value = new AtomicReference<>(2);

                try {
                    while (true) {
                        value.set(exchanger.exchange(value.get()));
                        System.out.println("Thread B has Value : " + value.get());
                        TimeUnit.SECONDS.sleep(2);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        /**
         * Thread A has Value : 2
         * Thread B has Value : 1
         * Thread A has Value : 1
         * Thread B has Value : 2
         * Thread B has Value : 1
         * Thread A has Value : 2
         * Thread A has Value : 1
         * Thread B has Value : 2
         * Thread B has Value : 1
         * Thread A has Value : 2
         */
    }
}
